package tmall.bean;

import java.util.Date;

/** 
* @author Kr: 
* @version 创建时间：2017年4月20日 下午9:38:08 
* 类说明 用户评价的类
*/
public class Review {
	private int id;
	private String content;//评价内容
	private User user;//评价内容创建者
	private Product product;//被评价的商品
	private Date createDate;//评价创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
		
}