package org.qingshan.demo.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Decoder;


@SuppressWarnings("restriction")
public class HTTPBasicAuthorizeAttribute implements Filter {

    private static enum ResultStatusCode {
        OK, PERMISSION_DENIED
    }

    private static String Name = "testUsername";
    private static String Password = "testPassword";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ResultStatusCode resultStatusCode = checkHTTPBasicAuthorize(request);
        if (resultStatusCode != ResultStatusCode.OK) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);

            ObjectMapper mapper = new ObjectMapper();

            httpResponse.getWriter().write(mapper.writeValueAsString("验证失败!"));
            return;
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    private ResultStatusCode checkHTTPBasicAuthorize(ServletRequest request) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String auth = httpRequest.getHeader("Authorization");
            if ((auth != null) && (auth.length() > 6)) {
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0) {
                    auth = auth.substring(6, auth.length());
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null) {
                        String[] UserArray = decodedAuth.split(":");

                        if (UserArray != null && UserArray.length == 2) {
                            if (UserArray[0].compareTo(Name) == 0
                                    && UserArray[1].compareTo(Password) == 0) {
                                return ResultStatusCode.OK;
                            }
                        }
                    }
                }
            }
            return ResultStatusCode.PERMISSION_DENIED;
        } catch (Exception ex) {
            return ResultStatusCode.PERMISSION_DENIED;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

}


