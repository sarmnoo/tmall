package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * @author Kr:
 * @version 创建时间：2017年4月20日 下午8:54:51 
 * 类说明 产品信息的类,用于展示给买家的一些信息
 */
public class Product {

	private int id; // 产品id
	private Category category; // 分类
	private String name; // 产品名字
	private String subTitle; // 产品小标题
	private float orignalPrice; // 产品价格
	private float promotePrice; // 产品促销价
	private Date createDate; // 创建日期
	private int stock; // 库存
	private int reviewCount; // 评价数
	private int saleCount; // 销量
	private ProductImage firstProductImage;//用于显示默认图片,从productSingleImages集合中取出第一个来
	private List<ProductImage> productImage; // 产品图片
	private List<ProductImage> productSingleImage; // 产品单个图片
	private List<ProductImage> productDetailImage; // 产品详情图片

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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public float getOrignalPrice() {
		return orignalPrice;
	}

	public void setOrignalPrice(float orignalPrice) {
		this.orignalPrice = orignalPrice;
	}

	public float getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}

	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}

	public List<ProductImage> getProductImage() {
		return productImage;
	}

	public void setProductImage(List<ProductImage> productImage) {
		this.productImage = productImage;
	}

	public List<ProductImage> getProductSingleImage() {
		return productSingleImage;
	}

	public void setProductSingleImage(List<ProductImage> productSingleImage) {
		this.productSingleImage = productSingleImage;
	}

	public List<ProductImage> getProductDetailImage() {
		return productDetailImage;
	}

	public void setProductDetailImage(List<ProductImage> productDetailImage) {
		this.productDetailImage = productDetailImage;
	}

}
