package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "addOptionalProductEmployeeServlet", value = "/add_optional_product")
public class AddOptionalProductEmployeeServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_db2.services/OptionalProduct")
    OptionalProductService optionalProductService;

    final String selectedOptionalsAttribute = "selected";
    final String optionalParameter = "optional";

    public AddOptionalProductEmployeeServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String addedId = request.getParameter(optionalParameter);
        if ( addedId == null ) {
            handleErrorRedirect(request, response, "Error while obtaining the data");
            return;
        }

        System.out.println("Adding: " + addedId);


        @SuppressWarnings("unchecked")
        List<OptionalProduct> optionals = (List<OptionalProduct>) request.getSession().getAttribute(selectedOptionalsAttribute);

        if ( optionals == null ) {
            optionals = new ArrayList<>();
        }



        // get the optional product from the db
        OptionalProduct newOptional = optionalProductService.findOptionalProductById(Integer.parseInt(addedId));

        // check if the optional has already been selected.
        if ( optionals.contains(newOptional) ) {
            handleErrorRedirect(request, response, "Selected product already in the package");
            return;
        }

        // add the optional product to the list of already selected optionals
        optionals.add(newOptional);

        // update the session's attribute with the new list of selected optionals
        request.getSession().setAttribute(selectedOptionalsAttribute, optionals);

        handleSuccessRedirect(request, response);
    }

    private void handleErrorRedirect(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        System.out.println("Handling error " + error);
        request.getSession().setAttribute("error", error);
        response.sendRedirect(request.getServletContext().getContextPath() + "/load_optionals");
    }

    private void handleSuccessRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // redirecting to the page that loads the optionals
        response.sendRedirect(request.getServletContext().getContextPath() + "/load_optionals");
    }
}
