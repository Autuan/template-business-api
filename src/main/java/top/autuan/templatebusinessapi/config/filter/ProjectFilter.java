package top.autuan.templatebusinessapi.config.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.autuan.templatebusinessapi.config.filter.wrapper.ProjectRequestWrapper;

import java.io.IOException;

//@Component
//@WebFilter(urlPatterns = "/*")
public class ProjectFilter extends HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {// NOSONAR
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ProjectRequestWrapper requestWrapper = new ProjectRequestWrapper(request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {// NOSONAR
    }
}
