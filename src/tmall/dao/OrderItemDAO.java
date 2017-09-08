package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.util.DBUtil;

public class OrderItemDAO {

	/**
	 * 获取订单项目总数
	 * @return 订单项目总数
	 */
	public int getTotal() {
		String sql = "select count(*) from orderitem";
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
	 * 获取销量,即用户在提交订单后的订单项目中number数据的和
	 * @param pid 产品id
	 * @return
	 */
	public int getSaleCount(int pid) {
		int total= 0;
		String sql ="select sum(number)  from orderitem where pid="+pid;
		try(Connection conn = DBUtil.getConnection();Statement st =conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				total= rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 添加一个订单项目信息,即放到购物车里面,订单在未提交上去之前可能没有订单信息
	 * 
	 * @param bean
	 *            被更新的订单项目信息对象
	 */
	public void add(OrderItem bean) {
		String sql = "insert into orderitem values(null,?,?,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			// 订单项在创建的时候是没有订单信息的,要在结算的时候才有订单信息
			// 订单项:买的每件物品的信息 订单: 多个或者一个订单项的付款信息
			if (null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一个订单项目信息,即删除掉购物车里面的东西
	 * 
	 * @param id
	 *            订单项目id
	 */
	public void delete(int id) {
		String sql = "delete * from orderitem where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个订单项目信息,即放到购物车里面,订单在未提交上去之前可能没有订单信息
	 * 
	 * @param bean
	 *            被更新的订单项目信息对象
	 */
	public void update(OrderItem bean) {
		String sql = "update orderitem set pid=?,oid=?,uid=?,number=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			// 更新订单,但是直到付款前,订单信息还是没有的
			if (null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 找出相应的订单项目明细
	 * 
	 * @param id
	 *            订单项目id
	 * @return 对应的订单项目明细
	 */
	public OrderItem get(int id) {
		String sql = "select * from orderitem where id =" + id;
		OrderItem bean = null;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				// 如果有订单信息,则设置这个订单项目的订单信息,没有则不设置,因为在购物车里还没有创建订单信息
				if (-1 != rs.getInt(3)) {
					bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				}
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 查询用户购物车里面所有的订单项目
	 * 
	 * @param uid
	 *            用户id
	 * @return 购物车里面的订单项目集合
	 */
	public List<OrderItem> listByUser(int uid) {
		return listByUser(uid, 0, Short.MAX_VALUE);
	}

	/**
	 * 查询找出用户购物车里面的订单项目
	 * 
	 * @param uid
	 *            用户id
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询条目数
	 * @return 购物车里面的订单项目条数集合
	 */
	public List<OrderItem> listByUser(int uid, int start, int count) {
		String sql = "select * from orderitem where uid=? and oid=-1 order by id desc limit ?,?";
		List<OrderItem> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 用户可能有多个订单,所以根据订单信息查询相应的订单项目数
	 * 
	 * @param oid
	 *            订单id
	 * @return 此订单信息下的所有订单项目数
	 */
	public List<OrderItem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}

	/**
	 * 用户可能有多个订单,根据订单信息分页查询出相应的订单项目
	 * 
	 * @param oid
	 *            订单id
	 * @param start
	 *            查询起点
	 * @param count
	 *            查询条目数
	 * @return 相应条目数的订单项目List集合
	 */
	public List<OrderItem> listByOrder(int oid, int start, int count) {
		String sql = "select * from orderitem where oid=? order by id desc limit ?,?";
		List<OrderItem> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	/**
	 * 设置单个订单中订单项目的总价, [应该是点击订单的时候用于显示下面的订单项目信息用的]
	 * @param o 订单信息
	 */
	public void fill(Order o){
		List<OrderItem> ois =listByOrder(o.getId());
		float total = 0;//总价
		for(OrderItem oi :ois){
			total += oi.getNumber()*oi.getProduct().getOrignalPrice(); //计算总价
		}
		o.setTotal(total); //设置总价
		o.setOrderItems(ois); //填充订单项目到订单中
	}
	
	/**
	 * 设置所有订单中订单项目的总价和订单数量,[应该是点击订单的时候用于显示下面的订单项目信息用的]
	 * @param os 订单信息
	 */
	public void fill(List<Order> os){
		for(Order o:os){ //使用for循环依次取出每个订单
			List<OrderItem> ois =listByOrder(o.getId()); //将每个订单中的订单项目取出来
			float total =0;//总价
			int totalNumber=0;//订单数量
			for(OrderItem oi :ois){
				total += oi.getNumber()*oi.getProduct().getOrignalPrice(); //计算总价 
				totalNumber+=oi.getNumber();//计算订单数量
			}
			o.setTotal(total); //设置总价
			o.setOrderItems(ois);//填充订单项目到订单中
			o.setTotalNumber(totalNumber);//设置订单总数
		}
	}
}
