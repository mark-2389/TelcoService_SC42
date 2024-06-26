package it.polimi.db2.telcoservice_sc42.web.servlets;

import it.polimi.db2.telcoservice_sc42.exception.CredentialErrorException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import it.polimi.db2.telcoservice_sc42.ejb.services.ClientService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "credentialServlet", value = "/register")
public class CredentialServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.ejb.services/LoginService")
    private ClientService clientService;

    public CredentialServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST called");
        handleRequest(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        request.getSession().setAttribute(SessionAttributeRegistry.error, null);

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            clientService.addClient(username, email, password);
        } catch ( NonUniqueClientException e ) {
            System.out.println("Client already in database");
            handleErrorRedirect(request, response, "Client already registered");
            return;
        } catch (CredentialErrorException e ) {
            System.out.println("Credential error");
            handleErrorRedirect(request, response, "Client already registered");
            return;
        }

        String redirectPage = "/index.jsp";

        if ( request.getSession().getAttribute(BuySessionRegistry.selectedPackage) != null ) {
            // if a package has already been selected the user was in the confirmation page
            redirectPage = "/HTML/confirmation.jsp";
        }

        response.sendRedirect(getServletContext().getContextPath() + redirectPage);
    }

    /**
     * Handle the redirection when an error occurs.
     *
     * When an error occurs the "error" attribute of the session is set so that the user can be informed.
     * @param request the http request.
     * @param response the http response.
     * @param error a string that describes the error. This string is directly prompted to the user.
     * @throws IOException if an error during redirection occurs.
     */
    private void handleErrorRedirect(HttpServletRequest request, HttpServletResponse response, String error) throws IOException{
        String caller = getCaller(request);
        request.getSession().setAttribute(SessionAttributeRegistry.error, error);

        if ( caller != null ) {
            response.sendRedirect(getServletContext().getContextPath() + "/" + caller);
        }
    }

    /**
     * Return the jsp page that called this servlet.
     *
     * @param request the http request for this servlet.
     * @return the jsp page (the path to the jsp page) as a string. No leading (at the beginning) backslash is inserted,
     * The file extension is added at the end of the file.
     */
    private String getCaller(HttpServletRequest request) {
        if ( request.getParameterValues("landingRegister") != null ) {
            return "index.jsp";
        }

        if ( request.getParameterValues("register") != null ) {
            return "HTML/registration.jsp";
        }

        return null;
    }

    public void destroy() {
    }
}
