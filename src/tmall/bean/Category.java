package tmall.bean;

import java.util.List;

/**
 * @author Kr:
 * @version 创建时间：2017年4月20日 下午8:48:18
 *  类说明 产品分类 包括产品分类ID,产品分类名字name,产品分类下各种产品products,产品分类名称下的各产品列表行数productsByRow
 */
public class Category {


	private String name;	// 产品分类名字
	private int id;	// 产品分类ID
	List<Product> products;	// 产品名字
	List<List<Product>> productsByRow; //即一个分类又对应多个 List<Product>，
									  //提供这个属性是为了在首页竖状导航的分类名称右边显示产品列表
									  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}

	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}

	@Override
	/**表示重写toString方法，当打印Category对象的时候，会打印其名称。 
	*在实际业务的时候并没有调用，在测试的过程中会调用到。
	**/
	public String toString() {
		return "Category[name" + name + "]";
	}

}
