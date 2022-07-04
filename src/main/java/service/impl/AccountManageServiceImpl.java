package service.impl;

import DAO.AccountDAO;
import controllers.AccountManageController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import model.UserInfo;
import service.AccountManageService;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountManageServiceImpl implements AccountManageService {
    private AccountManageController accountManageController;

    public AccountManageServiceImpl(AccountManageController accountManageController) {
        this.accountManageController = accountManageController;
    }

    @Override
    public void deleteAccount(String userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccountDAO accountDAO = new AccountDAO();
                boolean result = false;
                try {
                    result = accountDAO.deleteUserInfoByUserId(userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    boolean finalResult = result;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            accountManageController.deleteAccountResult_Callback(finalResult);
                        }
                    });
                }
            }

        }).start();

    }

    @Override
    public void addAccount(UserInfo userInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //去除密码中的空格，若去除后密码中的空格后为空字符串则直接设置为默认密码：123456
                String password = userInfo.getPassword();
                if (password.contains(" ")) {
                    password = password.replace(" ", "");
                    userInfo.setPassword(password);
                }
                if (password.equals("")) {
                    userInfo.setPassword("123456");
                }

                AccountDAO accountDAO = new AccountDAO();
                boolean result = false;
                try {
                    result = accountDAO.InsertUserInfo(userInfo);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    accountManageController.addAccountResult_Callback(result);
                }
            }
        }).start();
    }

    @Override
    public void editAccount(UserInfo newUserInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccountDAO accountDAO = new AccountDAO();
                boolean result = false;
                try {
                    result = accountDAO.updateUserInfo(newUserInfo);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    boolean finalResult = result;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            accountManageController.editAccountResult_Callback(finalResult);
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public void getAccounts(UserInfo userInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccountDAO accountDAO = new AccountDAO();
                String condition = "";
                boolean isFirst = true;
                if (userInfo.getUserId() != null && !userInfo.getUserId().equals("")) {
                    condition += "where userId = " + userInfo.getUserId();
                    isFirst = false;
                }
                if (userInfo.getOwner() != null && !userInfo.getOwner().equals("")) {
                    if (isFirst == false) {
                        condition += " and ";
                    } else {
                        condition += "where ";
                    }
                    condition += "owner = " + "'" + userInfo.getOwner() + "'";
                }

                ObservableList<UserInfo> observableList = null;

                try {
                    ArrayList<UserInfo> userInfoList = accountDAO.selectUserInfoByCondition(condition);
                    //create ObservableList from ArrayList
                    observableList = javafx.collections.FXCollections.observableArrayList(userInfoList);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    ObservableList<UserInfo> ObservableList_temp = observableList;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            accountManageController.searchAccount_Callback(ObservableList_temp);
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public void resetPassword(UserInfo userInfo) {

    }


}
