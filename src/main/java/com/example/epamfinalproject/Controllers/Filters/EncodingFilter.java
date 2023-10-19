package com.example.epamfinalproject.Controllers.Filters;

import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebFilter(filterName = "encodingFilter")
public class EncodingFilter implements Filter {
  private static final Logger log = LogManager.getLogger(EncodingFilter.class);
  private String encoding;

  @Override
  public void init(FilterConfig config) throws ServletException {
    encoding = config.getInitParameter("requestEncoding");
    if (encoding == null) encoding = "UTF-8";
  }

  @Override
  public void destroy() {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    log.debug("Filter starts");

    request.setCharacterEncoding(encoding);
    response.setCharacterEncoding(encoding);
    response.setContentType("text/html; charset=UTF-8");

    log.debug("Filter finished");
    chain.doFilter(request, response);
  }
}
