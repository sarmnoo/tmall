package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {

	/**
	 * 获取某个分类下的产品总数
	 * 
	 * @param cid
	 *            分类ID
	 * @return 分类的产品总数
	 */
	public int getTotal(int cid) {
		String sql = "select count(*) from product where cid=" + cid;
		int total = 0;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 新增一个产品
	 * 
	 * @param bean
	 *            添加的产品对象
	 */
	public void add(Product bean) {
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				bean.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据产品主键ID删除一个产品
	 * 
	 * @param id
	 *            产品id
	 */
	public void delete(int id) {
		String sql = "delete from product where id=" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个产品数据
	 * 
	 * @param bean
	 *            被更新的产品对象
	 */
	public void update(Product bean) {
		String sql = "update product set name=?,subTitle=?,orignalPrice=?,promotePrice=?,stock=?,cid=?,createDate=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(8, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据产品id获取一个产品
	 * 
	 * @param id
	 *            该产品的id
	 * @return 返回此id对应的产品对象
	 */
	public Product get(int id) {
		String sql = "select * from product where id=" + id;
		Product bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new Product();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSubTitle(rs.getString(3));
				bean.setOrignalPrice(rs.getFloat(4));
				bean.setPromotePrice(rs.getFloat(5));
				bean.setStock(rs.getInt(6));
				bean.setCategory(new CategoryDAO().get(rs.getInt(7)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 列出某个分类下的所有产品
	 * 
	 * @param cid
	 *            分类id
	 * @return 返回此分类id下的所有产品对象
	 */
	public List<Product> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}

	/**
	 * 分类产品的分页查询
	 * 
	 * @param cid
	 *            产品分类id
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询条目数
	 * @return 指定分类id的查询条目数的product对象集合
	 */
	public List<Product> list(int cid, int start, int count) {
		List<Product> beans = new ArrayList<>();
		String sql = "select * from product where cid =? order by id desc limit ?,?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSubTitle(rs.getString(3));
				bean.setOrignalPrice(rs.getFloat(4));
				bean.setPromotePrice(rs.getFloat(5));
				bean.setStock(rs.getInt(6));
				bean.setCategory(new CategoryDAO().get(rs.getInt(7)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 列出所有产品
	 * 
	 * @return 产品对象集合
	 */
	public List<Product> list() {
		return list(0, Short.MAX_VALUE);
	}

	/**
	 * 产品分页查询
	 * 
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询条目数
	 * @return 返回产品对象集合
	 */
	public List<Product> list(int start, int count) {
		List<Product> beans = new ArrayList<>();
		String sql = "select * from product  order by id desc limit ?,?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSubTitle(rs.getString(3));
				bean.setOrignalPrice(rs.getFloat(4));
				bean.setPromotePrice(rs.getFloat(5));
				bean.setStock(rs.getInt(6));
				bean.setCategory(new CategoryDAO().get(rs.getInt(7)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 填充某个分类下的产品
	 * 
	 * @param c
	 *            接受一个分类对象
	 */
	public void fill(Category c) {
		List<Product> ps = this.list(c.getId());
		c.setProducts(ps);
	}

	/**
	 * 填充所有分类下的产品
	 * 
	 * @param cs
	 *            分类对象集合
	 */
	public void fill(List<Category> cs) {
		for (Category c : cs) {
			fill(c);
		}
	}

	/**
	 * 为多个分类设置productsByRow属性 ，暂时理解不清这个方法的写法；
	 * how2j文中解释：
	 * 假设一个分类恰好对应40种产品，那么这40种产品本来是放在一个集合List里。 可是，在页面上显示的时候，需要每8种产品，放在一列
	 * 为了显示的方便，我把这40种产品，按照每8种产品方在一个集合里的方式，拆分成了5个小的集合，这5个小的集合里的每个元素是8个产品。
	 * 这样到了页面上，显示起来就很方便了。 否则页面上的处理就会复杂不少。
	 * 
	 * @param cs
	 *            产品分类集合
	 */
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;// 设置每个产品的行数为8
		for (Category c : cs) {// 循环取出list对象集合的每一个分类
			List<Product> products = c.getProducts(); // 取得其中一个分类对象下的所有产品集合
			List<List<Product>> productsByRow = new ArrayList<>(); // 设置一个产品行数集合
			for (int i = 0; i < products.size(); i += productNumberEachRow) { //
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			c.setProductsByRow(productsByRow);
		}
	}

	/**
	 * 设置产品的默认第一张图片
	 * 
	 * @param p
	 *            产品对象
	 */
	public void setFirstProductImage(Product p) {
		List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_Single);
		if (!pis.isEmpty()) {
			p.setFirstProductImage(pis.get(0));
		}
	}

	/**
	 * 设置某个产品的销量和评价
	 * 
	 * @param p
	 *            产品对象
	 */
	public void setSaleAndReviewNumber(Product p) {
		int saleCount = new OrderItemDAO().getSaleCount(p.getId());
		p.setSaleCount(saleCount);

		int reviewCount = new ReviewDAO().getCount(p.getId());
		p.setReviewCount(reviewCount);
	}

	/**
	 * 根据关键字查找相应的产品
	 * 
	 * @param keyword
	 *            产品关键字
	 * @param strat
	 *            查询起点
	 * @param count
	 *            查询条目数
	 * @return 返回一个products对象集合
	 */
	public List<Product> search(String keyword, int strat, int count) {
		List<Product> beans = new ArrayList<>();
		if (null == keyword || 0 == keyword.trim().length())
			return beans;
		String sql = "select * from product where name like ? limit ?,?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "%" + keyword.trim() + "%");
			ps.setInt(2, strat);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSubTitle(rs.getString(3));
				bean.setOrignalPrice(rs.getFloat(4));
				bean.setPromotePrice(rs.getFloat(5));
				bean.setStock(rs.getInt(6));
				bean.setCategory(new CategoryDAO().get(rs.getInt(7)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

}
