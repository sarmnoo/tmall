package tmall.bean;
/** 
* @author Kr: 
* @version 创建时间：2017年4月20日 下午9:33:30 
* 类说明 属性值,与属性对应,界面显示产品的相关属性说明的值
*/
public class PropertyValue {
	
	private int id; //属性值id
	private Product product;//具体的产品
	private Property property;//具体产品的属性
	private String value;//具体产品的属性值
	
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
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
