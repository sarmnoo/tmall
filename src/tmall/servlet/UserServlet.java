package tmall.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.User;
import tmall.util.Page;

/**
* @author Kr
* 创建时间：2017年9月4日 下午10:48:03
* 类说明：
*/
public class UserServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {

		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {

		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<User> us = userDAO.list(page.getStart(),page.getCount());
		int total = userDAO.getTotal();
		page.setTotal(total);
		request.setAttribute("us", us);
		request.setAttribute("page", page);
		
		return "admin/listUser.jsp";
	}

}
