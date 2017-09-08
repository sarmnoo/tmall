package tmall.bean;
/** 
* @author Kr: 
* @version 创建时间：2017年4月20日 下午9:14:14 
* 类说明 在界面显示相应的产品图片和说明
*/
public class ProductImage {
	
	private int id;//图片id
	private Product product;//产品信息,用于获取设置关联的产品id
	private String type;//图片类型,分为单个图片和详情图片
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
