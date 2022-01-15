package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.entities.OrderStatus;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.Validity;
import it.polimi.db2.telcoservice_sc42.services.OrderService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "paymentServlet", value = "/Payment")
public class PaymentServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer orderId = (Integer) session.getAttribute(BuySessionRegistry.orderId);

        // if it is a new purchase, orderId is null since the order has to be created
        if (orderId == null) {
            String username = (String) session.getAttribute(SessionAttributeRegistry.username);
            int validityId = ((Validity) session.getAttribute(BuySessionRegistry.chosenValidity)).getId();
            int packageId = ((ServicePackage) session.getAttribute(BuySessionRegistry.selectedPackage)).getId();
            Date subscriptionDate = Date.valueOf((String) session.getAttribute(BuySessionRegistry.chosenSubscription));
            List<Integer> optionals = getOptionalProducts(session);

            // first we create the order
            orderId = orderService.createOrder(username, validityId, packageId, subscriptionDate, optionals);

            if (orderId == null) {
                System.out.println("An error occurred while creating the order");
                return;
            }
        }

        List<String> attributes = BuySessionRegistry.getAllFields();
        System.out.println("ATTRIBUTES: " + attributes);
        for (String attribute: attributes) {
            System.out.println("Setting " + attribute + " to null");
            session.setAttribute(attribute, null);
        }

        String msg;
        if ( handleExternalPayment(request) ) {
            // if the payment has been successful mark the order as accepted
            orderService.setOrderStatus(orderId, OrderStatus.ACCEPTED);

            msg = "Your order has been confirmed. ";
        } else {
            // if the payment has been unsuccessful, mark the order as rejected.
            // The user is set to insolvent by a trigger.
            orderService.setOrderStatus(orderId, OrderStatus.REJECTED);

            msg = "The payment has been rejected, try again later. ";
        }

        session.setAttribute(BuySessionRegistry.paymentMsg, msg);

        response.sendRedirect(request.getServletContext().getContextPath() + "/HTML/PaymentResult.jsp");
    }

    private List<Integer> getOptionalProducts(HttpSession session) {
        List<OptionalProduct> optionals = (List<OptionalProduct>) session.getAttribute(BuySessionRegistry.chosenOptionals);

        if ( optionals == null ) return new ArrayList<>();

        return optionals.stream().
                    map(OptionalProduct::getId).
                        collect(Collectors.toList());
    }

    private boolean handleExternalPayment(HttpServletRequest request) {
        String fail = request.getParameter("fail");

        System.out.println(fail);

        if ( fail == null ) return Math.random() > 0.5;
        if (fail.equals("1")) return false;
        if (fail.equals("0")) return true;

        return Math.random() > 0.5;
    }
}
