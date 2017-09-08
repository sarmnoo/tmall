package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author Kr:
 * @version 创建时间：2017年5月2日 下午9:26:04 类说明：过滤器
 */
public class BackServletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String contextPath = request.getServletContext().getContextPath(); // 获取应用路径:/tmall
		String uri = request.getRequestURI();// request.getRequestURI()：取出访问的uri:
												// /tmall/admin_category_list
		uri = StringUtils.remove(uri, contextPath);// 截掉/tmall，得到路径/admin_category_list
		if (uri.startsWith("/admin_")) { // 判断其是否以/admin开头,如果是，那么就取出两个_之间的字符串，category，并且拼接成/categoryServlet，通过服务端跳转到/categoryServlet
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet"; // 取出两个_之间的字符串，category																							
			// 在跳转之前，还取出了list字符串，然后通过request.setAttribute的方式，借助服务端跳转，传递到categoryServlet里去
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			req.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
