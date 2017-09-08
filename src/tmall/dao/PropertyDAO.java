package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

/**
 * 
 * @author Kr
 * @version2017年4月21日
 * 类说明: Category分类相应属性的CRUD方法
 */
public class PropertyDAO {

	/**
	 * 添加一个分类属性
	 * @param bean 分类属性对象,包含了分类 类对象Category,Property属性名字name.
	 */
	public void add(Property bean) {
		String sql = "insert into property values(null,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个分类对象
	 * @param bean 分类属性对象,包含了分类 类对象Category,Property属性名字name,Property对象的id,
	 */
	public void update(Property bean) {
		String sql = "update property set cid=? ,name=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一个分类属性
	 * @param id 被删除的分类属性Property的id值.
	 */
	public void delete(int id) {
		String sql = "delete from property where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据分类属性Property的id值获取相关的分类属性信息
	 * @param id 分类属性Property的id值
	 * @return 这个id值对应的Property对象
	 */
	public Property get(int id) {
		Property bean = new Property();

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "select * from Property where id = " + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				bean.setName(name);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				bean.setId(id);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 根据属性name和分类id获取 相应的属性property对象
	 * @param name 属性名字
	 * @param cid Category分类对象id
	 * @return Property对象
	 */
	public Property get(String name, int cid) {
		String sql = "select * from property where cid =? and name =?";
		Property bean = null;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cid);
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new Property();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				Category category = new CategoryDAO().get(rs.getInt(cid));
				bean.setCategory(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 获取某种分类下的属性总数，在分页显示的时候会用到
	 * @param cid Category分类ID
	 * @return 总条目数
	 */
	public int getTotal(int cid) {
		String sql = "select count(*) from property where cid =" + cid;
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
	 * 查询某个分类下的的所有Property属性对象
	 * @param cid Category对象id
	 * @return 返回分类[Category]对象下的Property属性的List集合
	 */
	public List<Property> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}

	/**
	 * 查询某个分类下的的属性对象
	 * @param cid Category分类id
	 * @param start 查询起点
	 * @param count 查询的条目数
	 * @return 返回Category分类id下的Property对象的List集合
	 */
	public List<Property> list(int cid, int start, int count) {
		String sql = "select * from property where cid=? order by id desc limit ?,?";
		List<Property> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			{
				while (rs.next()) {
					Property bean = new Property();
					bean.setId(rs.getInt("id"));
					bean.setCategory(new CategoryDAO().get(cid));
					bean.setName(rs.getString("name"));
					beans.add(bean);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
