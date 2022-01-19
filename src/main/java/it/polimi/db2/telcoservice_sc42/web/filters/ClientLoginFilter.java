package it.polimi.db2.telcoservice_sc42.web.filters;


import java.io.IOException;

import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/ClientLoginFilter")
public class ClientLoginFilter implements Filter {
    public ClientLoginFilter() { }

    public void destroy() { }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filtering for client");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println(req.getServletContext().getContextPath());

        if ( req.getServletContext().getContextPath().endsWith("index.jsp") ) {
            chain.doFilter(request, response);
        }

        String loginPath = req.getServletContext().getContextPath() + "/index.jsp";



        HttpSession s = req.getSession();
        if (s.isNew() || s.getAttribute(SessionAttributeRegistry.username) == null) {
            res.sendRedirect(loginPath);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}

