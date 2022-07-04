package service.impl;

import DAO.AccountDAO;

import controllers.LoginController;
import javafx.application.Platform;
import model.UserInfo;
import service.LoginService;

import javax.management.loading.PrivateClassLoader;
import java.sql.SQLException;

/**
 * @description: ��¼���ܵķ����
 * @author: ����
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
                    //��userInfo.getUserId()==null�����˻������벻��
                    if (userInfo.getUserId() == null) {
                        loginController.loginResult_Callback(false, 1, null);
                    }
                    //�˺�������ȷ������Ҫ�Ƚ��û�������˺������Ƿ�������ݿ��е��˺�����
                    else {
                        //����û�������˻�����Ȩ��С�ڵ����˻������ݿ��е�Ȩ�ޣ����¼�ɹ�
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
     * @param InputAccountType �û�������˻�����
     * @param realAccountType  ���ݿ��е��˻�����
     * @return �û������˻����ͽϵ�ʱ���ظ������û��������ͽϸ�ʱ������������ͬʱ���� 0
     */
    private int compareAccountType(String InputAccountType, String realAccountType) {
        //����û�������˻������Ƿ�����û����˻����ͣ�ֻ�е����û����˻����Ͳ��ܵ�¼�������Ա�˺ſ���Ϊ��ͨ�û���¼
        int inputAccountLevel = 1;
        int realAccountLevel = 1;
//                        ���û�������˺����ͺ����ݿ���˺����ķֱ���ȼ����Ա�Ƚ�
        if (InputAccountType.equals("��������Ա")) {
            inputAccountLevel = 1;
        } else if (InputAccountType.equals("��������Ա")) {
            inputAccountLevel = 2;

        } else if (InputAccountType.equals("����")) {
            inputAccountLevel = 3;
        } else {
            inputAccountLevel = 999;
        }

        if (realAccountType.equals("��������Ա")) {
            realAccountLevel = 1;
        } else if (realAccountType.equals("��������Ա")) {
            realAccountLevel = 2;

        } else if (realAccountType.equals("����")) {
            realAccountLevel = 3;
        } else {
            realAccountLevel = 0;
        }
        return inputAccountLevel - realAccountLevel;
    }


}
