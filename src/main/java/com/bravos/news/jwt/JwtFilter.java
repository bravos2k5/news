package com.bravos.news.jwt;

import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class JwtFilter implements Filter {

    private final static List<String> BLACK_LIST = List.of(
            "/admin/users",
            "/admin/categories",
            "/admin/letters",
            "/api/admin"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        UserInfo user = (UserInfo) session.getAttribute("user");
        String requestURI = request.getRequestURI();

        if(requestURI.endsWith(".jsp")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if(requestURI.startsWith("/admin") || requestURI.startsWith("/api")) {

            if(user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (user.getRole() != Role.ADMIN) {
                for (String uri : BLACK_LIST) {
                    if(requestURI.startsWith(uri)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }

            String accessToken = getAccessToken(cookies);
            if(!JwtUtil.isValid(accessToken,user.getUsername())) {
                deleteAccessKey(cookies,response);
                session.removeAttribute("user");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            chain.doFilter(request,response);
            return;
        }
        chain.doFilter(request,response);
    }

    private String getAccessToken(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("accessToken")) {
                return cookie.getValue();
            }
        }
        return null;
    }



    private void deleteAccessKey(Cookie[] cookies, HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("accessToken")) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

}
