package service;

import model.UserInfo;
/**
 * @description: �˺Ź����ܵķ����
 * @author: ����
 * @date: 2022-7-4
 */
public interface AccountManageService {
    public void deleteAccount(String userId);

    public void addAccount(UserInfo userInfo);

    public void editAccount(UserInfo newUserInfo);

    public void getAccounts(UserInfo userInfo);

    public void resetPassword(UserInfo userInfo);
}
