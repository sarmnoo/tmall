/**
 * 
 */
package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

/**
* @author Kr
* @version 创建时间：2017年8月20日 下午1:25:49
* 类说明：
*/
public class PropertyServlet extends BaseBackServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tmall.servlet.BaseBackServlet#add(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		
		String propertyname = request.getParameter("name");
		int cid = Integer.parseInt(request.getParameter("category"));
		
		Property property = new Property();
		Category c = categoryDAO.get(cid);
		property.setName(propertyname);
		property.setCategory(c);
		
		propertyDAO.add(property);
		return "@admin_property_list?cid="+c.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tmall.servlet.BaseBackServlet#delete(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * tmall.util.Page)
	 */
	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("ptyid"));
		Property p = propertyDAO.get(pid);
		propertyDAO.delete(pid);		
		return "@admin_property_list?cid="+p.getCategory().getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tmall.servlet.BaseBackServlet#edit(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("ptyid"));		
		Property property = propertyDAO.get(pid);		
		request.setAttribute("property", property);		
		return "admin/editProperty.jsp";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tmall.servlet.BaseBackServlet#update(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * tmall.util.Page)
	 */
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		String propertyname = request.getParameter("propertyname");
		int pid = Integer.parseInt(request.getParameter("ptyid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		Property property = new Property();
		Category c = categoryDAO.get(cid);
		property.setName(propertyname);
		property.setId(pid);
		property.setCategory(c);
		propertyDAO.update(property);
		return "@admin_property_list?cid="+property.getCategory().getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tmall.servlet.BaseBackServlet#list(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		int total = propertyDAO.getTotal(cid);
		Category c = categoryDAO.get(cid);
		List<Property> propertylist = propertyDAO.list(cid, page.getStart(),page.getCount());
		
		//System.out.println("数量："+propertylist.size());
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		request.setAttribute("c", c);
		request.setAttribute("propertylist", propertylist);
		request.setAttribute("page", page);	
		
		return "admin/listProperty.jsp";
	}

}
