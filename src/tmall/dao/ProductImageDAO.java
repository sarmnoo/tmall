package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

/**
 * ProductImageDAO用于建立对于ProductImage对象的ORM映射
 * @author Kr
 *	2017年4月22日
 */
public class ProductImageDAO {
	//产品单个图片
	public static final String type_Single="type_Single";
	//产品详情图片
	public static final String type_detail="type_detail";
	
	/**
	 * 添加一个产品图片
	 * @param bean 产品图片对象
	 */
	public void add(ProductImage bean){
		String sql = "insert into productimage values(null,?,?)";
		try(Connection conn =DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ps.execute();
			//设置产品主键值
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()){
				bean.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除一个产品图片
	 * @param id 要删除的产品图片id
	 */
	public void delete(int id){
		String sql = "delete * from productimage where id="+id;
		try(Connection conn =DBUtil.getConnection();Statement st = conn.createStatement()) {
			st.execute(sql);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新一个产品图片 备注：业务上用不到;
	 * @param bean 更新的产品图片对象
	 */
	public void update(ProductImage bean){
	}
	
	/**
	 * 获取一个产品图片
	 * @param id 给定产品图片id,获取的产品图片
	 * @return 产品图片对象
	 */
	public ProductImage get(int id){
		String sql = "select * from productimage where id="+id;
		ProductImage bean = new ProductImage();
		try(Connection conn =DBUtil.getConnection();Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setType(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 获取产品图片总数，备注：未使用
	 * @return 返回一个整数
	 */
	public int getTotal(){
		String sql = "select count(*) from productimage";
		int total = 0;
		try(Connection conn =DBUtil.getConnection();Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/*
	public int getTotal(ProductImage bean,String type){
		String sql = "select count(*) from productimage where pid=? and type=?";
		int total = 0;
		try(Connection conn =DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	*/
	
	/**
	 * 获取ProductImage对象的List集合
	 * @param p 图像对应的产品
	 * @param type 图片类型
	 * @return ProductImage对象的List集合
	 */
	public List<ProductImage> list(Product p, String type){
		return list(p,type,0,Short.MAX_VALUE);
	}
	
	/**
	 * 产品图片的分页查询
	 * @param p 查询的产品
	 * @param type 产品图片类型
	 * @param start 查询起点
	 * @param count 查询条目数
	 * @return 对应的条目数的产品图片List集合
	 */
	public List<ProductImage> list(Product p, String type,int start,int count){
		String sql = "select * from productimage where type=? and cid = ? order by id desc limit ?,?";
		List<ProductImage> beans = new ArrayList<>();		
		try(Connection conn =DBUtil.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1,type);
			ps.setInt(2, p.getId());
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ProductImage bean = new ProductImage();
				bean.setId(rs.getInt("id"));
				bean.setProduct(p);
				bean.setType(type);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
