package it.polimi.db2.telcoservice_sc42.filters;


import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/EmployeeLoginFilter")
public class EmployeeLoginFilter implements Filter {
    public EmployeeLoginFilter() { }

    public void destroy() { }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if ( true ) return;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String loginPath = req.getServletContext().getContextPath() + "employee/login.jsp";

        HttpSession s = req.getSession();
        if (s.isNew() || s.getAttribute("id") == null) {
            res.sendRedirect(loginPath);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}

