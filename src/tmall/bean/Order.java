package tmall.bean;

import java.util.Date;
import java.util.List;

import tmall.dao.OrderDAO;

/**
 * @author Kr:
 * @version 创建时间：2017年4月20日 下午9:41:29 类说明 订单详情
 */
public class Order {
	private int id;
	private String orderCode;// 订单号
	private String address;// 地址
	private String post;// 邮编
	private String receiver; // 接收
	private String mobile;// 手机
	private String userMessage;// 用户信息
	private Date createDate;// 订单创建日期
	private Date payDate;// 支付日期
	private Date deliveryDate;// 发货时间
	private Date confirmDate;// 确认日期
	private User user;// 购买者
	private String status;// 订单状态
	private List<OrderItem> orderItems; // 详细的购买物品列表
	private float total;// 订单金额
	private int totalNumber;// 订单数量

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * 获取订单状态
	 */
	public String getStatus() {
		String desc="未知";
		switch(status){
			case OrderDAO.waitConfirm:
				desc="待确认";
				break;
			case OrderDAO.waitDelivery:
				desc="待发货";
			break;
			case OrderDAO.waitPay:
				desc="待支付";
				break;
			case OrderDAO.waitReview:
				desc="待评价";
				break;
			case OrderDAO.finish:
				desc="完成";
				break;
			case OrderDAO.delete:
				desc="删除";
				break;			
			default:
				desc="未知";
		}
		return desc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
