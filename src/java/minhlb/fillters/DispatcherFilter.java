/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.fillters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import minhlb.dtos.User;

/**
 *
 * @author Minh
 */
public class DispatcherFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    private final List<String> guest;
    private final List<String> user;
    private final List<String> admin;

    public DispatcherFilter() {
        guest = new ArrayList<>();
        guest.add("login.jsp");
        guest.add("index.jsp");
        guest.add("register.jsp");
        guest.add("verify.jsp");
        guest.add("LoginController");
        guest.add("FacebookController");
        guest.add("RegisterController");
        guest.add("SendVerifyController");
        guest.add("SearchCarController");
        guest.add("error.jsp");
        guest.add("header.jsp");
        guest.add("");

        user = new ArrayList<>();
        user.add("index.jsp");
        user.add("history.jsp");
        user.add("order_detail.jsp");
        user.add("view_cart.jsp");
        user.add("AddCartController");
        user.add("DelCartController");
        user.add("DelOrderController");
        user.add("FeedbackController");
        user.add("GetOrderDetailController");
        user.add("GetOrderHistoryController");
        user.add("LogoutController");
        user.add("OrderController");
        user.add("SearchCarController");
        user.add("SearchOrderController");
        user.add("UpdateCartController");
        user.add("error.jsp");
        user.add("header.jsp");
        user.add("");

        admin = new ArrayList<>();
        admin.add("index.jsp");
        admin.add("history.jsp");
        admin.add("order_detail.jsp");
        admin.add("LogoutController");
        admin.add("GetOrderHistoryController");
        admin.add("GetOrderDetailController");
        admin.add("SearchCarController");
        admin.add("SearchOrderController");
        admin.add("error.jsp");
        admin.add("header.jsp");
        admin.add("");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatcherFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatcherFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("DispatcherFilter:doFilter()");
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String url = null;
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1);
        HttpSession session = req.getSession(false);
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".ttf") || uri.endsWith(".woff") || uri.endsWith(".woff2") || uri.endsWith(".jpeg")) {
            chain.doFilter(request, response);
            return;
        }
        if (session == null || session.getAttribute("User") == null) {
            if (guest.contains(resource)) {
                url = resource;
            } else {
                req.setAttribute("AUTHEN_ERROR", "You need login to do this action!");
                url = "login.jsp";
            }
        } else {
            User acc = (User) session.getAttribute("User");
            if (acc.isIsAdmin() == true && admin.contains(resource)) {
                url = resource;
            } else if (acc.isIsAdmin()== false && user.contains(resource)) {
                url = resource;
            } else {
                req.setAttribute("ERROR_MSG", "You don't have permission to do this action!");
                url = "error.jsp";
            }
        }
//        if (resource.length() > 0) {
//            url = resource;
//        }
        if (url != null) {
            req.getRequestDispatcher(url).forward(request, response);
        } else {
            chain.doFilter(request, response);

        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("DispatcherFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DispatcherFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatcherFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
