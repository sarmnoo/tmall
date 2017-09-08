/**
 * 
 */
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

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

/**
 * @author Kr
 * @version 创建时间：2017年8月25日 上午10:48:50 类说明
 */
public class ProductImageServlet extends BaseBackServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tmall.servlet.BaseBackServlet#add(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

		InputStream is = null;// 创建文件输入流
		Map<String, String> params = new HashMap<>(); // 提交上传文件时的其他参数
		is = parseUpload(request, params);// 解析上传数据

		// 根据上传数据解析参数
		String type = params.get("type");// 获取图片类型，单个图片或者详情图片
		int pid = Integer.parseInt(request.getParameter("pid"));// 获取产品ID
		Product p = productDAO.get(pid);// 根据产品id，获得产品对象
		ProductImage pi = new ProductImage();// 创建图片对象，用于设置图片属性值
		pi.setProduct(p);// 设置图片所属的产品
		pi.setType(type);// 设置图片类型
		productImageDAO.add(pi);// 向数据库中添加图片对象

		// 生成文件
		String fileName = pi.getId() + ".jpg";// 设置图片名，根据图片id格式设置
		String imageFolder;// 定义图片文件夹
		String imageFolder_small = null;// 定义小图片文件夹
		String imageFolder_middle = null;// 定义中图片文件夹

		if (ProductImageDAO.type_Single.equals(pi.getType())) {
			imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
			imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		} else
			imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
		File f = new File(imageFolder, fileName);
		f.getParentFile().mkdirs();

		// 复制文件
		try {
			if (null != is && 0 != is.available()) {
				FileOutputStream fos = new FileOutputStream(f);
				byte[] b = new byte[1024 * 1024];
				int length = 0;
				while (-1 != (length = is.read(b))) {
					fos.write(b, 0, length);
				}
				fos.flush();
				
				BufferedImage img = ImageUtil.change2jpg(f);
				ImageIO.write(img, "jpg", f);
				if(ProductImageDAO.type_Single.equals(pi.getType())){
					File file_small = new File(imageFolder_small,fileName);
					File file_middle = new File(imageFolder_middle,fileName);
					ImageUtil.resizeImage(f, 56, 56, file_small);
					ImageUtil.resizeImage(f, 217, 190, file_middle);
				}
				fos.close();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return "@admin_productImage_list?pid="+p.getId();
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
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage pi = productImageDAO.get(id);
		productImageDAO.delete(pi.getId());
		if (ProductImageDAO.type_Single.equals(pi.getType())) {
			String imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
			String imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			String imageFolder_middle = request.getSession().getServletContext()
					.getRealPath("img/productSingle_middle");

			File f_single = new File(imageFolder_single, pi.getId() + ".jpg");
			f_single.delete();
			File f_small = new File(imageFolder_small, pi.getId() + ".jpg");
			f_small.delete();
			File f_middle = new File(imageFolder_middle, pi.getId() + ".jpg");
			f_middle.delete();
		} else {
			String imgFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
			File f_detail = new File(imgFolder_detail, pi.getId() + ".jpg");
			f_detail.delete();
		}
		return "@admin_productImage_list?pid=" + pi.getProduct().getId();
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		List<ProductImage> productSingleImage = productImageDAO.list(p, ProductImageDAO.type_Single);
		List<ProductImage> productDetailImage = productImageDAO.list(p, ProductImageDAO.type_detail);
		request.setAttribute("simg", productSingleImage);
		request.setAttribute("dimg", productDetailImage);
		request.setAttribute("p", p);
		return "admin/listProductImages.jsp?pid=" + p.getId();
	}

}
