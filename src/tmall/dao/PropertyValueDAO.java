package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

/**
 * 用于建立对PropertyValue对象的ORM映射
 * @author Kr
 *2017年4月23日
 */
public class PropertyValueDAO {

	/**
	 * 获取全部属性值的个数,业务中未使用
	 * 
	 * @return 属性值总数
	 */
	public int getTotal() {
		String sql = "select count(*) from propertyvalue";
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
	 * 添加一个属性值
	 * 
	 * @param bean
	 *            被添加的属性值对象
	 */
	public void add(PropertyValue bean) {
		String sql = "insert into propertyvalue values(null,?,?,?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
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
	 * 删除一个属性值
	 * 
	 * @param id
	 *            属性值的id
	 */
	public void delete(int id) {
		String sql = "delete * from propertyvalue where id =" + id;
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个属性值
	 * 
	 * @param bean
	 *            被更新的属性值对象
	 */
	public void update(PropertyValue bean) {
		String sql = "update propertyvalue set pid=?,ptid=?,value=? where id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取一个属性值,业务中未使用
	 * 
	 * @param id
	 *            属性值id
	 * @return 返回id对应的属性值对象PropertyValue
	 */
	public PropertyValue get(int id) {
		String sql = "select * from propertyvalue where id =" + id;
		PropertyValue bean = new PropertyValue();
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setProperty(new PropertyDAO().get(rs.getInt(3)));
				bean.setValue(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 根据Product产品id,Property属性id获取属性值对象
	 * 
	 * @param ptid
	 *            Property对象id
	 * @param pid
	 *            Product对象id
	 * @return 对应的属性值对象PropertyValue
	 */
	public PropertyValue get(int ptid, int pid) {
		String sql = "select * from propertyvalue where ptid =? and pid=?";
		PropertyValue bean = new PropertyValue();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, ptid);
			ps.setInt(2, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 初始化某个产品对应的属性值 ,如果没有属性值,则设置属性值
	 * @param p 产品对象product
	 */
	public void init(Product p) {
		//根据产品获取产品分类，然后根据分类获取分类的所有属性
		List<Property> pts = new PropertyDAO().list(p.getCategory().getId());
		////遍历每一个属性
		for(Property pt:pts){
			//根据属性和产品获取属性值
			PropertyValue pv = get(pt.getId(), p.getId());
			//如果属性值不存在，就创建一个属性值对象
			if(null==pv){
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);
				this.add(pv);
			}			
		}
	}

	/**
	 * 获取产品的全部属性值
	 * 
	 * @param pid
	 *            产品Product对象id
	 * @return 对应的产品的全部PropertyValue属性值List集合
	 */
	public List<PropertyValue> list(int pid) {
		String sql = "select * from propertyvalue where pid=? order by ptid desc";
		List<PropertyValue> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setProperty(new PropertyDAO().get(rs.getInt(3)));
				bean.setValue(rs.getString(4));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 取得全部属性值,业务中未使用
	 * @return
	 */
	public List<PropertyValue> list() {
		return list(0, Short.MAX_VALUE);
	}

	/**
	 * 分页查询,业务中未使用
	 * @param start
	 * @param count
	 * @return
	 */
	public List<PropertyValue> list(int start, int count) {
		String sql = "select count(*) from propertyvalue order by id limit ?,?";
		List<PropertyValue> beans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setProperty(new PropertyDAO().get(rs.getInt(3)));
				bean.setValue(rs.getString(4));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
