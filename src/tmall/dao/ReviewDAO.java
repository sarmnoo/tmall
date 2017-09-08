package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Review;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

/**
 * 用于建立对Review对象的ORM映射
 * 
 * @author Kr 2017年4月23日
 */
public class ReviewDAO {

	/**
	 * 获取评论总数
	 * 
	 * @return 评论总数
	 */
	public int getCount(int pid) {
		String sql = "select count(*) from review where pid="+pid;
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
	 * 添加一条产品评论
	 * 
	 * @param bean
	 *            评论对象Review
	 */
	public void add(Review bean) {
		String sql = "insert into review values(null,?,?,?,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
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
	 * 根据主键id删除一条评论
	 * 
	 * @param id
	 *            此评论对应的id
	 */
	public void delete(int id) {
		String sql = "select * from review where id=" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一条评论
	 * 
	 * @param bean
	 *            接收一个Review对象
	 */
	public void update(Review bean) {
		String sql = "update review set content=?,uid=?,pid=?,createDate=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(5, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出此产品对应的所有评论
	 * 
	 * @param pid
	 *            产品id
	 * @return 返回Review对象评论集合
	 */
	public List<Review> list(int pid) {
		return list(pid, 0, Short.MAX_VALUE);
	}

	/**
	 * 评论的分页查询功能
	 * 
	 * @param pid
	 *            产品id
	 * @param start
	 *            查询起点
	 * @param count
	 *            要查询的条目数
	 * @return 返回指定产品评论条目数的list集合
	 */
	public List<Review> list(int pid, int start, int count) {
		String sql = "select count(*) from review where pid =? order by pid limit ?,?";
		List<Review> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Review bean = new Review();
				bean.setId(rs.getInt(1));
				bean.setContent(rs.getString(2));
				bean.setUser(new UserDAO().get(rs.getInt(3)));
				bean.setProduct(new ProductDAO().get(pid));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
