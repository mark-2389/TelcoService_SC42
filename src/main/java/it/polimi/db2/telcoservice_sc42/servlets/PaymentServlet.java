package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.OrderStatus;
import it.polimi.db2.telcoservice_sc42.services.OrderService;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "paymentServlet", value = "/Payment")
public class PaymentServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OrderService")
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getSession().getAttribute(SessionAttributeRegistry.username);
        int validityId = SafeParser.safeParseInteger( (String) request.getSession().getAttribute("chosen_validity") );
        int packageId = (Integer) request.getSession().getAttribute("selectedPackage");
        Date subscriptionDate = Date.valueOf( (String) request.getSession().getAttribute("chosen_subscription"));

        // first we create the order
        Integer orderId = orderService.createOrder(username, validityId, packageId, subscriptionDate);

        if ( orderId == null ) {
            System.out.println("An error occurred while creating the order");
            return;
        }

        String msg;
        if ( handleExternalPayment() ) {
            // if the payment has been successful mark the order as accepted
            orderService.setOrderStatus(orderId, OrderStatus.ACCEPTED);
            msg = "Your order has been confirmed. ";
        } else {
            // if the payment has been unsuccessful, mark the order as rejected.
            // The user is set to insolvent by a trigger.
            orderService.setOrderStatus(orderId, OrderStatus.REJECTED);

            msg = "The payment has been rejected, try again later. ";
        }

        request.getSession().setAttribute(SessionAttributeRegistry.error, msg);

        response.sendRedirect(request.getServletContext().getContextPath() + "/HomePage");
    }

    private boolean handleExternalPayment() {
        return Math.random() > 0.5;
    }
}
