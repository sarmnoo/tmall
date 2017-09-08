package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Kr:
 * @version 创建时间：2017年4月21日 上午10:48:59 类说明
 *          数据库工具类,初始化驱动,并且提供一个getConnection方法用于获取数据库连接
 */
public class DBUtil {
	static String loginName = "root";
	static String password = "admin";
	static String ip = "127.0.0.1";
	static int port = 3306;
	static String database = "tmall";
	static String encoding = "UTF-8";
	static {//使用静态代码块加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取数据库连接
	 * @return 一个数据库连接对象Connection
	 * @throws SQLException
	 * url地址格式为:  jdbc:mysql://127.0.0.1:3306/tmall?encoding=utf-8 ;
	 */
	public static Connection getConnection() throws SQLException{
		String url=String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port,database,encoding);
		return DriverManager.getConnection(url,loginName,password);
	}	
}
