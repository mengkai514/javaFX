package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 数据库JDBC直连和关闭连接。
 * 
 * @author 李育桥，卢星宇，黄涛
 *黄涛更新：简单实现了一个数据库连接池，目的在于配合多线程编程使用
 */
public class DBConnector {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	//localhost:3306 IP+端口形式链接数据库应用,同学们自己试试把localhost改为127.0.0.1能用吗。3306是mysql默认端口。
	//booklib 是自己创建的数据库“库名”，user和pass对应数据库账号密码。
	//useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC（这段话是JDBC 8.XXX版本后需要加上的SSL，5.XXX版本的同学不用管）。
	private static final String DB_URL = "jdbc:mysql://bj-cynosdbmysql-grp-l38rjgu6.sql.tencentcdb.com:21549/DigitalTubeDetectDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "Ht1657673964";
	//最小连接数
	private static final int minCount = 1;
	//最大连接数
	private static final int maxCount = 10;
	//当前连接数
	private static  int currentCount=0;
	private static final LinkedList<Connection> connectionsPool = new LinkedList<>();


	/**
	 * 创建一个新连接
	 * @return
	 */
	private static Connection connect() {
		// 注册 JDBC 驱动
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			System.out.println("注册 JDBC 驱动 失败！");
			e.printStackTrace();
		}


		// 打开链接
		Connection connection;
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			currentCount++;
			return connection;
		} catch (SQLException e) {
			System.out.println("连接数据库失败");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从连接池中获取一个连接
	 * @return
	 */
	public static synchronized Connection getConnection()  {
		//如果连接池中有，则直接返回一个连接
		if(connectionsPool.size()>0){
			return connectionsPool.remove(0);


		}else {//如果连接池中没有，则尝试new一个，或等待其他线程释放资源。
			//如果达到最大连接数,则等待其他线程释放资源
			if(currentCount>=maxCount){
				//等待其他线程释放资源
				while (connectionsPool.size()<=0);
				return connectionsPool.remove(0);
			}else{//如果没有达到最大连接数,则直接new一个连接
				Connection connection=connect();
				if(connection!=null){
					connectionsPool.add(connection);
				}
				return connection;
			}
		}
	}

	/**
	 * 把连接返回给连接池
	 * @param connection
	 */
	public static synchronized void giveBackConnection(Connection connection)  {
		connectionsPool.add(connection);
	}

	/**
	 * 把连接返回给连接池，并关闭指定需要关闭的preparedStatement
	 * @param connection
	 * @param preparedStatement 需要关闭的preparedStatement
	 * @throws SQLException
	 */
	public static synchronized void giveBackConnection(Connection connection,PreparedStatement preparedStatement) {
		//关闭preparedStatement
		try {
			if(preparedStatement!=null){
				preparedStatement.close();
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		//压入connection
		connectionsPool.add(connection);
	}

	public static void close() {
		// 关闭所有连接
		connectionsPool.forEach(x->{
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		});
	}
}
