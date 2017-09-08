package tmall.util;

/**
 * @author Kr:
 * @version 创建时间：2017年5月23日 下午8:45:09 类说明
 */
public class Page {

	int start;
	int count;
	int total;	
	String param;

	/**
	 * 获取起始位置
	 * 
	 * @return 起始位置
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 设置起始位置
	 * 
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取每页显示的数量
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置每页显示数量
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 获取总共有多少条数据
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置总共的条数据
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 获取参数
	 * 
	 * @return
	 */
	public String getParam() {
		return param;
	}

	/**
	 * 设置参数
	 * 
	 * @param param
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 默认构造函数
	 * 
	 * @param start
	 * @param count
	 */
	public Page(int start, int count) {
		super();
		this.start = start;
		this.count = count;
	}

	/**
	 * 判断是否有前一页
	 * 
	 * @return
	 */
	public boolean isHasPreviouse() {
		if (start == 0)
			return false;
		return true;
	}

	/**
	 * 判断是否有后一页
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		if (start == getLast())
			return false;
		return true;
	}

	/**
	 * 获取最后一页的数值是多少
	 * 
	 * @return
	 */
	public int getLast() {
		int last;
		if (0 == total % count)
			last = total - count;
		else
			last = total - total % count;
		last = last < 0 ? 0 : last;
		return last;
	}

	/**
	 * 根据 每页显示的数量count以及总共有多少条数据total，计算出总共有多少页
	 * 
	 * @return
	 */
	public int getTotalPage() {
		int totalPage;
		// 假设总数是50，是能够被5整除的，那么就有10页
		if (0 == total % count){
			totalPage = total / count;
		}
		// 假设总数是51，不能够被5整除的，那么就有11页
		else{
			totalPage = total / count + 1;
		}
		//
		if (0 == totalPage){
			totalPage = 1;
		}
		return totalPage;
	}
}
