package service.impl;

import DAO.AccountDAO;
import controllers.LoginController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UserInfo;
import service.LoginService;

import java.sql.SQLException;

/**
 * @description: 登录功能的服务层
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class LoginServiceImpl implements LoginService {
    private AccountDAO accountDAO;
    private LoginController loginController;

    public LoginServiceImpl(LoginController loginController) {
        this.loginController = loginController;
        accountDAO = new AccountDAO();
    }

    @Override
    public void login(String userId, String password, String accountType) {
        //create a thread to check the userId and password
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = null;
                try {
                    userInfo = accountDAO.selectUserByIdAndPassword(userId, password);
                    //若userInfo.getUserId()==null代表账户名密码不对
                    if (userInfo.getUserId() == null) {
                        loginController.loginResult_Callback(false, 1, null);
                    }
                    //账号密码正确，但需要比较用户输入的账号类型是否低于数据库中的账号类型
                    else {
                        //如果用户输入的账户类型权限小于等于账户在数据库中的权限，则登录成功
                        if (compareAccountType(accountType, userInfo.getAccountType()) <= 0) {
                            loginController.loginResult_Callback(true, 0, accountType);
                        } else {

                            loginController.loginResult_Callback(true, 2, userInfo.getAccountType());
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    loginController.loginResult_Callback(false, 3, null);
                } finally {

                }
            }

        }).start();
    }

    public void setLoginResult(boolean loginResult) {
        if (loginResult == true) {
            System.out.println("login success");
        } else {
            System.out.println("login failed");
        }
    }


    /**
     * @param InputAccountType 用户输入的账户类型
     * @param realAccountType  数据库中的账户类型
     * @return 用户输入账户类型较低时返回负数，用户输入类型较高时返回正数，相同时返回 0
     */
    private int compareAccountType(String InputAccountType, String realAccountType) {
        //检查用户输入的账户类型是否低于用户的账户类型，只有低于用户的账户类型才能登录，如管理员账号可作为普通用户登录
        int inputAccountLevel = 1;
        int realAccountLevel = 1;
//                        将用户输入的账号类型和数据库的账号内心分别赋予等级，以便比较
        if (InputAccountType.equals("技术操作员")) {
            inputAccountLevel = 1;
        } else if (InputAccountType.equals("技术管理员")) {
            inputAccountLevel = 2;

        } else if (InputAccountType.equals("厂长")) {
            inputAccountLevel = 3;
        } else {
            inputAccountLevel = 999;
        }

        if (realAccountType.equals("技术操作员")) {
            realAccountLevel = 1;
        } else if (realAccountType.equals("技术管理员")) {
            realAccountLevel = 2;

        } else if (realAccountType.equals("厂长")) {
            realAccountLevel = 3;
        } else {
            realAccountLevel = 0;
        }
        return inputAccountLevel - realAccountLevel;
    }


}
