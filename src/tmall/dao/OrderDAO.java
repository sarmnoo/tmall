package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

/**
 * OrderDAO用于建立对于Order对象的ORM映射
 * 
 * @author Kr 2017年4月23日
 */
public class OrderDAO {
	
	// 用于表示订单类型，在实体类Order的getStatusDesc方法中就用到了这些属性
	public static final String waitPay = "waitPay"; // 待付款
	public static final String waitDelivery = "waitDelivery";// 待发货-
	public static final String waitConfirm = "waitConfirm";// 待确认
	public static final String waitReview = "waitReview";// 待评价
	public static final String finish = "finish";// 完成
	public static final String delete = "delete";// 删除

	/**
	 * 获取全部订单数量
	 * 
	 * @return 订单数量
	 */
	public int getTotal() {
		String sql = "select count(*) from order_";
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
	 * 新增一个订单
	 * 
	 * @param bean
	 *            订单对象Order
	 */
	public void add(Order bean) {
		String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6, bean.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
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
	 * 根据ID删除一个订单
	 * 
	 * @param id
	 *            被删除订单的id号
	 */
	public void delete(int id) {
		String sql = "delete * from order_ where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个订单信息
	 * 
	 * @param bean
	 *            更新的订单对象
	 */
	public void update(Order bean) {
		String sql = "update order_ set orderCode=?,address=?,post=?,receiver=?,mobile=?,userMessage=?,createDate=?,payDate=?,deliveryDate,confirmDate=?,uid=?,status=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6, bean.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			ps.setInt(13, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id主键获取一个订单
	 * 
	 * @param id
	 *            订单id
	 * @return 返回指定id的订单对象
	 */
	public Order get(int id) {
		String sql = "select * from order_ where id=" + id;
		Order bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new Order();
				bean.setId(rs.getInt(1));
				bean.setOrderCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setPost(rs.getString(4));
				bean.setReceiver(rs.getString(5));
				bean.setMobile(rs.getString(6));
				bean.setUserMessage(rs.getString(7));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp(9)));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp(10)));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp(11)));
				bean.setUser(new UserDAO().get(rs.getInt(12)));
				bean.setStatus(rs.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 列出所有订单
	 * 
	 * @return Order订单对象的List集合
	 */
	public List<Order> list() {
		return list(0, Short.MAX_VALUE);
	}

	/**
	 * 所有订单的分页查询
	 * 
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询的条目数
	 * @return 相应的条目数的 Order订单对象的List集合
	 */
	public List<Order> list(int start, int count) {
		String sql = "select * from  order_  order by id desc limit ?,? ";
		List<Order> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt(1));
				bean.setOrderCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setPost(rs.getString(4));
				bean.setReceiver(rs.getString(5));
				bean.setMobile(rs.getString(6));
				bean.setUserMessage(rs.getString(7));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp(9)));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp(10)));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp(11)));
				bean.setUser(new UserDAO().get(rs.getInt(12)));
				bean.setStatus(rs.getString(13));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 查询指定用户的所有订单(去掉某种订单状态，通常是"delete")
	 * 
	 * @param uid
	 *            购买者的id
	 * @param excludedStatus
	 *            订单状态
	 * @return
	 */
	public List<Order> list(int uid, String excludedStatus) {
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}

	/**
	 * 查询指定用户的订单(去掉某种订单状态，通常是"delete")
	 * 
	 * @param uid
	 *            购买者的id
	 * @param excludedStatus
	 *            订单状态
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询的条目数
	 * @return 对应用户指定订单状态的的所有订单
	 */
	public List<Order> list(int uid, String excludedStatus, int start, int count) {
		String sql = "select * from order_ where uid=? and status!=\"excludedStatus\" order by uid desc limit ?,? ";
		List<Order> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt(1));
				bean.setOrderCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setPost(rs.getString(4));
				bean.setReceiver(rs.getString(5));
				bean.setMobile(rs.getString(6));
				bean.setUserMessage(rs.getString(7));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp(9)));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp(10)));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp(11)));
				bean.setUser(new UserDAO().get(rs.getInt(12)));
				bean.setStatus(rs.getString(13));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
