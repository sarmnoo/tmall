package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

/**
 * @author Kr:
 * @version 创建时间：2017年4月21日 上午11:24:17 类说明 分类的CRUD方法
 */
public class CategoryDAO {

	/**
	 * 根据分类名字表添加一条产品分类记录
	 * 
	 * @param bean
	 *            产品分类对象
	 */
	public void add(Category bean) {
		String sql = "insert into category values(null,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			String name = bean.getName();
			System.out.println("DAO里面的name:"+name);
			ps.setString(1, name);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();// 获取自动生成的主键
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 删除一个产品分类
	 * 
	 * @param id
	 *            要删除的产品分类id
	 */
	public void delete(int id) {
		String sql = "delete from category where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 更新一个产品分类
	 * 
	 * @param bean
	 *            要更新的产品分类
	 */
	public void update(Category bean) {
		String sql = "update category set name=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			String name = bean.getName();
			int id = bean.getId();
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取一个产品分类
	 * 
	 * @param id
	 *            通过id获取
	 * @return 返回id对应的产品分类
	 */
	public Category get(int id) {
		String sql = "select * from category where id=" + id;
		Category bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new Category();
				String name = rs.getString("name");
				bean.setId(id);
				bean.setName(name);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 分页查询
	 * 
	 * @param start
	 *            查询的起点
	 * @param count
	 *            查询的条目数
	 * @return 返回Category的list集合对象
	 */
	public List<Category> list(int start, int count) {
		String sql = "select * from category order by id desc limit ?,?";
		List<Category> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category bean = new Category();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 查询全部产品分类
	 * 
	 * @return 返回Category的list集合对象
	 */
	public List<Category> list() {
		return list(0, Short.MAX_VALUE);
	}

	/**
	 * 获取分类总数
	 * 
	 * @return 返回分类总数 类型为int
	 */
	public int getTotal() {
		String sql = "select count(*) from category";
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
}
