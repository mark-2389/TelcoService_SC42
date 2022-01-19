package it.polimi.db2.telcoservice_sc42.web.servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GoToSalesReportPageServlet", value = "/SalesReportPage")
public class GoToSalesReportPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = handleRejectRequest(request, response, "employee/login.jsp", "id");
        System.out.println("SALES REPORT ID: " + id);

        if ( id != null ) {
            // redirect to the servlet that loads all optionals
            response.sendRedirect(getServletContext().getContextPath() + "/load_reports");
        }
    }

    /**
     * Check if the request should be rejected and redirect it if that's the case.
     *
     * @param request the http request.
     * @param response the http response.
     * @param redirectPath the path to which the request should be redirected. The path starts from the servletContext
     *                     contextPath and should not contain the leading "/" character.
     * @param attributeName the name of the session's attribute that contains the username (or id) of the user that is
     *                      logging in.
     * @return the user's username (or id in case of Employee) if the no reject redirect was needed, null otherwise.
     * @throws IOException if the redirect fails.
     */
    private String handleRejectRequest(HttpServletRequest request, HttpServletResponse response, String redirectPath, String attributeName) throws IOException {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute(attributeName);
        if ( session.isNew() || username == null ) {
            String loginPath = getServletContext().getContextPath() + "/" + redirectPath;
            response.sendRedirect(loginPath);
        }

        return username;
    }
}
