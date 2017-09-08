package tmall.bean;

/**
 * 
 * @author PD
 * @version 创建时间2017-04-20 用户信息的类
 * 	类说明:  包括用户ID,用户名name,用户密码password和 获取本用户的匿名名称，在评价的时候显示用户名使用。getAnonymousName()
 */
public class User {

	private int id;	// 用户ID
	private String name;	// 用户名
	private String password;	// 用户密码

	/**
	 * 设置匿名评价的用户名
	 * 
	 * @return 匿名用户
	 */
	public String getAnonymousName() {
		if (null == name) {
			return null;
		}
		if (name.length() <= 1) {
			return "*";
		}
		if (name.length() == 2) {
			return name.substring(0, 1) + "*";
		}
		//名字超过3个字符以上的将中间的字符逐个替代为*
		char[] cs = name.toCharArray();
		for (int i = 1; i <= name.length(); i++) {
			cs[i] = '*';
		}
		return cs.toString();
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
