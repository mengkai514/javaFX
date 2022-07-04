package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * ���ݿ�JDBCֱ���͹ر����ӡ�
 * 
 * @author �����ţ�¬�������
 *���θ��£���ʵ����һ�����ݿ����ӳأ�Ŀ��������϶��̱߳��ʹ��
 */
public class DBConnector {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	//localhost:3306 IP+�˿���ʽ�������ݿ�Ӧ��,ͬѧ���Լ����԰�localhost��Ϊ127.0.0.1������3306��mysqlĬ�϶˿ڡ�
	//booklib ���Լ����������ݿ⡰��������user��pass��Ӧ���ݿ��˺����롣
	//useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC����λ���JDBC 8.XXX�汾����Ҫ���ϵ�SSL��5.XXX�汾��ͬѧ���ùܣ���
	private static final String DB_URL = "jdbc:mysql://bj-cynosdbmysql-grp-l38rjgu6.sql.tencentcdb.com:21549/DigitalTubeDetectDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "Ht1657673964";
	//��С������
	private static final int minCount = 1;
	//���������
	private static final int maxCount = 10;
	//��ǰ������
	private static  int currentCount=0;
	private static final LinkedList<Connection> connectionsPool = new LinkedList<>();


	/**
	 * ����һ��������
	 * @return
	 */
	private static Connection connect() {
		// ע�� JDBC ����
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			System.out.println("ע�� JDBC ���� ʧ�ܣ�");
			e.printStackTrace();
		}


		// ������
		Connection connection;
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			currentCount++;
			return connection;
		} catch (SQLException e) {
			System.out.println("�������ݿ�ʧ��");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �����ӳ��л�ȡһ������
	 * @return
	 */
	public static synchronized Connection getConnection()  {
		//������ӳ����У���ֱ�ӷ���һ������
		if(connectionsPool.size()>0){
			return connectionsPool.remove(0);


		}else {//������ӳ���û�У�����newһ������ȴ������߳��ͷ���Դ��
			//����ﵽ���������,��ȴ������߳��ͷ���Դ
			if(currentCount>=maxCount){
				//�ȴ������߳��ͷ���Դ
				while (connectionsPool.size()<=0);
				return connectionsPool.remove(0);
			}else{//���û�дﵽ���������,��ֱ��newһ������
				Connection connection=connect();
				if(connection!=null){
					connectionsPool.add(connection);
				}
				return connection;
			}
		}
	}

	/**
	 * �����ӷ��ظ����ӳ�
	 * @param connection
	 */
	public static synchronized void giveBackConnection(Connection connection)  {
		connectionsPool.add(connection);
	}

	/**
	 * �����ӷ��ظ����ӳأ����ر�ָ����Ҫ�رյ�preparedStatement
	 * @param connection
	 * @param preparedStatement ��Ҫ�رյ�preparedStatement
	 * @throws SQLException
	 */
	public static synchronized void giveBackConnection(Connection connection,PreparedStatement preparedStatement) {
		//�ر�preparedStatement
		try {
			if(preparedStatement!=null){
				preparedStatement.close();
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		//ѹ��connection
		connectionsPool.add(connection);
	}

	public static void close() {
		// �ر���������
		connectionsPool.forEach(x->{
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		});
	}
}
