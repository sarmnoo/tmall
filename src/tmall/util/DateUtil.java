package tmall.util;

/**
 * @author Kr:
 * @version 创建时间：2017年4月21日 上午11:11:32 类说明 用于sql.date和util.date日期的互转.
 */
public class DateUtil {
	/**
	 * util.date转换为sql数据库所用的date格式,要具体到时间,使用 java.sql.Timestamp方法转换
	 * 
	 * @param d
	 *            java.util.Dated对象
	 * @return 返回java.sql.Timestamp对象包含具体时间, 如接收的date为空则返回null
	 */
	public static java.sql.Timestamp d2t(java.util.Date d) {
		if (null == d) {
			return null;
		}
		return new java.sql.Timestamp(d.getTime());
	}

	/**
	 * 将数据库中的日期java.sql.Timestamp转换为java.util.Date中的date格式
	 * 
	 * @param t
	 *            java.sql.Timestamp对象
	 * @return 返回 java.util.Date对象,  如接收的date为空则返回null
	 */
	public static java.util.Date t2d(java.sql.Timestamp t) {
		if (null == t)
			return null;
		return new java.util.Date(t.getTime());
	}
}
