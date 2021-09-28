package ru.kpfu.itis.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(value = {"/profile", "/else"})
public class SignInFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Cookie cookies[] = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("auth")) {
                    System.out.println("Куки из браузера");
                    System.out.println(cookie.getValue());
                }
            }
        } else {
            System.out.println("Пользователь не аутентифицирован!!!");
        }

    }

    @Override
    public void destroy() {

    }
}
