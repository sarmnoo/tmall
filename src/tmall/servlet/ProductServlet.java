/**
 * 
 */
package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.Page;

/**
* @author 
* @version 创建时间：2017年8月21日 下午3:00:49
* 类说明
*/
/**
 * @author coito
 *
 */
public class ProductServlet extends BaseBackServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tmall.servlet.BaseBackServlet#add(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		Product p = new Product();

		p.setCategory(c);
		p.setName(name);
		p.setOrignalPrice(orignalPrice);
		p.setStock(stock);
		p.setPromotePrice(promotePrice);
		p.setSubTitle(subTitle);

		productDAO.add(p);

		return "@admin_product_list?cid=" + c.getId();
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
		int pid = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(pid);
		int cid = p.getCategory().getId();
		productDAO.delete(pid);

		return "@admin_product_list?cid=" + cid;
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
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		Category c = productDAO.get(pid).getCategory();
		request.setAttribute("c", c);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";
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
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		int pid = Integer.parseInt(request.getParameter("pid"));

		Product p = productDAO.get(pid);
		Category c = p.getCategory();
		p.setCategory(c);
		p.setName(name);
		p.setOrignalPrice(orignalPrice);
		p.setStock(stock);
		p.setPromotePrice(promotePrice);
		p.setSubTitle(subTitle);
		productDAO.update(p);

		return "@admin_product_list?cid=" + c.getId();
	}

	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		request.setAttribute("p", p);
		
		//List<Property> pts = propertyDAO.list(p.getCategory().getId());
		propertyValueDAO.init(p);
		
		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
		request.setAttribute("pvs", pvs);
		
		return "admin/editProductValue.jsp";
	}

	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		
		PropertyValue pv = propertyValueDAO.get(pvid);
		pv.setValue(value);
		propertyValueDAO.update(pv);
		return "%success";
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
		List<Product> ps = productDAO.list(cid, page.getStart(), page.getCount());
		int total = productDAO.getTotal(cid);
		Category c = categoryDAO.get(cid);
		page.setTotal(total);
		page.setParam("&cid=" + c.getId());
		request.setAttribute("ps", ps);
		request.setAttribute("c", c);
		request.setAttribute("page", page);

		return "admin/listProducts.jsp";
	}

}
