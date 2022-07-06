package DAO;

import model.UserInfo;
import utils.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @description: 账号管理相关的数据库操作层面
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class AccountDAO {
    //    通过账号和密码返回用户个数，不返回用户信息
    public int countUserByIdAndPassword(String userId, String password) throws SQLException {
        //获取数据库连接
        //c
        Connection connection = DBConnector.getConnection();

        String sql = "Select Count(*) from userInfo where userId=? And password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, password);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
//        保存结果
        int re = result.getInt(1);
//        归还链接，关闭resultset
        DBConnector.giveBackConnection(connection, preparedStatement);
        result.close();
        return re;
    }

    //      通过账号和密码查询用户信息
    public UserInfo selectUserByIdAndPassword(String userId, String password) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Select * from userInfo where userId=? And password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, password);
        ResultSet result = preparedStatement.executeQuery();
//        从查询结果中获取数据，保存在Userinfo中
        UserInfo userInfo = new UserInfo();
        if (result.next()) {
            userInfo.setUserId(result.getString("userId"));
            userInfo.setPassword(result.getString("password"));
            userInfo.setAccountType(result.getString("accountType"));
        }

//        归还链接，关闭resultset
        DBConnector.giveBackConnection(connection, preparedStatement);
        result.close();
        return userInfo;
    }

    public ArrayList<UserInfo> selectAllUserInfo() throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Select * from userInfo where userId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,userId);
        ResultSet result = preparedStatement.executeQuery();
        //transfer ResultSet to ArrayList<UserInfo>
        ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
        while (result.next()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(result.getString("userId"));
            userInfo.setPassword(result.getString("password"));
            userInfo.setOwner(result.getString("owner"));
            userInfo.setAccountType(result.getString("accountType"));
            userInfoList.add(userInfo);
        }
//        归还链接，关闭resultset
        DBConnector.giveBackConnection(connection, preparedStatement);
        result.close();
        return userInfoList;
    }

    public UserInfo selectUserInfoByUserId(String userId) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Select * from userInfo where userId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        ResultSet result = preparedStatement.executeQuery();
        //transfer ResultSet to ArrayList<UserInfo>
        UserInfo userInfo = new UserInfo();
        while (result.next()) {
            userInfo.setUserId(result.getString("userId"));
            userInfo.setPassword(result.getString("password"));
            userInfo.setOwner(result.getString("owner"));
            userInfo.setAccountType(result.getString("accountType"));
        }
        DBConnector.giveBackConnection(connection, preparedStatement);
        result.close();
        return userInfo;
    }

    public ArrayList<UserInfo> selectUserInfoByCondition(String condition) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Select * from userInfo " + condition;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet result = preparedStatement.executeQuery();
        //transfer ResultSet to ArrayList<UserInfo>
        ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
        while (result.next()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(result.getString("userId"));
            userInfo.setPassword(result.getString("password"));
            userInfo.setOwner(result.getString("owner"));
            userInfo.setAccountType(result.getString("accountType"));
            userInfoList.add(userInfo);
        }
        DBConnector.giveBackConnection(connection, preparedStatement);
        result.close();
        return userInfoList;
    }

    public boolean deleteUserInfoByUserId(String userId) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Delete from userInfo where userId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            DBConnector.giveBackConnection(connection, preparedStatement);
            return true;
        }
        DBConnector.giveBackConnection(connection, preparedStatement);
        return false;
    }

    public boolean InsertUserInfo(UserInfo userInfo) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Insert into userInfo(userId,password,owner,accountType) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userInfo.getUserId());
        preparedStatement.setString(2, userInfo.getPassword());
        preparedStatement.setString(3, userInfo.getOwner());
        preparedStatement.setString(4, userInfo.getAccountType());
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            DBConnector.giveBackConnection(connection, preparedStatement);
            return true;
        }
        DBConnector.giveBackConnection(connection, preparedStatement);
        return false;
    }

    public boolean updateUserInfo(UserInfo userInfo) throws SQLException {
        //获取数据库连接
        Connection connection = DBConnector.getConnection();

        String sql = "Update userInfo set password=?,owner=?,accountType=? where userId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userInfo.getPassword());
        preparedStatement.setString(2, userInfo.getOwner());
        preparedStatement.setString(3, userInfo.getAccountType());
        preparedStatement.setString(4, userInfo.getUserId());
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            DBConnector.giveBackConnection(connection, preparedStatement);
            return true;
        }
        DBConnector.giveBackConnection(connection, preparedStatement);
        return false;
    }


}
