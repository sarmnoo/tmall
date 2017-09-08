package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

/**
 * 
 * @author Kr
 * @version 2017年4月21日 类说明 用户信息的类,
 * 包含基本CRUD方法和用于支持其他业务的方法
 * 
 */
public class UserDAO {

	/**
	 * 获取用户总数
	 * 
	 * @return 返回整数.用户总数
	 */
	public int getTotal() {
		String sql = "select count(*) from user";
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
	 * 增加一个用户
	 * 
	 * @param bean
	 *            接收的用户对象
	 */
	public void add(User bean) {
		String sql = "insert into user values(null,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
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
	 * 通过id删除一个用户
	 * 
	 * @param id
	 *            被删除用户的主键id
	 */
	public void delete(int id) {
		String sql = "delete * from user where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个用户信息
	 * 
	 * @param bean
	 *            接收一个被更新的用户对象
	 */
	public void update(User bean) {
		String sql = "update user set name=?,password=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过id获取一个User对象
	 * 
	 * @param id
	 *            主键id
	 * @return 返回对应的User对象,如果没有找到则返回null
	 */
	public User get(int id) {
		String sql = "select * from user where id=" + id;
		User bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new User();
				bean.setId(id);
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 通过名字获取User对象
	 * 
	 * @param name
	 *            用户名
	 * @return 返回对应的User对象,如果没有找到则返回null
	 */
	public User get(String name) {
		String sql = "select * from user where name=" + name;
		User bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(name);
				bean.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 验证登陆的用户名和密码是否正确
	 * 
	 * @param name
	 *            用户名
	 * @param password
	 *            用户密码
	 * @return 返回对应的User对象,如果没有找到则返回null;
	 */
	public User get(String name, String password) {
		String sql = "select * from user where name=? and password=?";
		User bean = null;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 判断注册的用户名是否存在
	 * 
	 * @param name
	 *            注册的用户名
	 * @return 返回true或者false, true:表示注册的用户名已经存在,false:注册的用户名可以使用
	 * 
	 */
	public boolean isExist(String name) {
		User user = get(name);
		if (user == null)
			return false;
		else
			return true;
	}

	/**
	 * 获取全部用户
	 * 
	 * @return User对象的List集合
	 */
	public List<User> list() {
		return list(0, Short.MAX_VALUE);
	}

	/**
	 * 分页查询用户
	 * 
	 * @param start
	 *            查询的起点
	 * @param count
	 *            需要查询的条目数
	 * @return 指定起点的条目数的User对象的List集合
	 */
	public List<User> list(int start, int count) {
		String sql = "select * from user order by id desc limit ?,?";
		List<User> beans = new ArrayList<>();		
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

}
