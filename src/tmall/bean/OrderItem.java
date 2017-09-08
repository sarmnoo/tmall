package tmall.bean;
/** 
* @author Kr: 
* @version 创建时间：2017年4月20日 下午9:51:08 
* 类说明 订单项目明细的类 ,包含一个订单的产品product,下单用户user,每个产品的订单数number,此订单项目产品明细的所对应的订单信息order
*/
public class OrderItem {
	private int id;
	private Product product;//订单项目明细中的产品
	private Order order;//此订单项目明细对应的订单详情状态信息
	private User user;//下单用户
	private int number;//产品订购数量
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}	
}
