package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

/**
 * @author Kr
 * 创建时间：2017年5月22日 下午7:15:30 
 * 类说明：产品分类servlet,对分类产品页面进行增删改查操作；
 */
public class CategoryServlet extends BaseBackServlet {
	
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);

		String name = params.get("name");
		//System.out.println("add页面获取到的name:"+name);
		Category c = new Category();
		c.setName(name);
		//System.out.println("add分类对象的name值："+c.getName());
		categoryDAO.add(c);

		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder, c.getId() + ".jpg");
		//file.getParentFile().mkdirs();
		
		try {
			if (null != is && 0 != is.available()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					byte b[] = new byte[1024 * 1024];
					int length = 0;
					while (-1 != (length = is.read(b))) {
						fos.write(b, 0, length);
					}
					fos.flush();
					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));

		categoryDAO.delete(id);
		return "@admin_category_list";
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Category c = categoryDAO.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		System.out.println(params);
		String name = params.get("name");		
		int id= Integer.parseInt(params.get("id"));
		
		Category c =new Category();
		c.setId(id);
		c.setName(name);
		categoryDAO.update(c);
		
		File imageFolder =new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		
		try{
			if(null!=is&&0!=is.available()){
				try(FileOutputStream fos = new FileOutputStream(file)){
					byte[] b = new byte[1024*1024];
					int length = 0;
					while(-1!=(length=is.read(b))){
						fos.write(b, 0, length);
					}
					fos.flush();
					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
				}
				catch (Exception e) {
					e.printStackTrace();
				}						
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_category_list";	
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		//System.out.println("totalPage:"+page.getTotalPage());
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);		
		return "admin/listCategory.jsp";		
	}
}
