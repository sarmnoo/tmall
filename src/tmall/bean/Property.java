package tmall.bean;

/**
 * @author Kr:
 * @version 创建时间：2017年4月20日 下午9:01:27 
 * 类说明 产品的属性 包括产品id,产品分类cid 产品属性名字name
 */
public class Property {

	private int id;	// 属性id
	private Category category;	 // 产品分类,通过分类获取分类下的各类产品
	private String name;	// 属性名字

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
