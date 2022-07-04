package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UserInfo;
import service.impl.AccountManageServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @description: 账户管理界面controller
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class AccountManageController implements Initializable {


    private ObservableList<UserInfo> userInfoList;
    @FXML
    private Button clean_button;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    //账号条件筛选一栏的组件
    @FXML
    private TextField userId_textFeild;

    @FXML
    private TextField owner_textFeild;

    @FXML
    private Button search_button;

    //表格部分的组件
    @FXML
    private TableView<UserInfo> table;

    @FXML
    private TableColumn<UserInfo, String> userId_tableCol;

    @FXML
    private TableColumn<UserInfo, String> accountType_tableCol;

    @FXML
    private TableColumn<?, ?> operations_tableCol;

    @FXML
    private TableColumn<UserInfo, String> password_tableCol;

    @FXML
    private TableColumn<UserInfo, String> owner_tableCol;


    //“当前选中账户”一栏的组件

    @FXML
    private TextField selectedUserId_TextField;

    @FXML
    private TextField selectedOwner_TextField;

    @FXML
    private ChoiceBox<String> selectedAccountType_ChoiceBox;

    @FXML
    private Button commitEditButton;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Button deleteButton;


    //添加账户一栏的组件
    @FXML
    private TextField addUserId_TextField;

    @FXML
    private TextField addOwner_TextField;

    @FXML
    private TextField addPassword_TextField;

    @FXML
    private ChoiceBox<String> addAccountType_ChoiceBox;

    @FXML
    private Button addAccountButton;


    @FXML
    void searchAccount(ActionEvent event) {
        String userId = userId_textFeild.getText();
        String owner = owner_textFeild.getText();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setOwner(owner);

        new AccountManageServiceImpl(this).getAccounts(userInfo);
    }

    /**
     * 回调函数，用于更新搜索结果在表格中的显示
     *
     * @param userInfoList
     */
    public void searchAccount_Callback(ObservableList<UserInfo> userInfoList) {
        table.setItems(userInfoList);
        table.refresh();
    }

    /**
     * 清空搜索条件
     *
     * @param event
     */
    @FXML
    void cleanTextField(ActionEvent event) {
        userId_textFeild.setText("");
        owner_textFeild.setText("");
    }


    /**
     * 获取鼠标点击的表格中的行，将这样对应的账户信息显示在“当前选中账户”一栏中
     *
     * @param event
     */
    @FXML
    void getSelectedItem(MouseEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        selectedUserId_TextField.setText(userInfo.getUserId());
        selectedOwner_TextField.setText(userInfo.getOwner());
        selectedAccountType_ChoiceBox.setValue(userInfo.getAccountType());
    }

    /**
     * 删除账户按钮的事件处理函数
     *
     * @param event
     */
    @FXML
    void deleteAccount(ActionEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        //需要多线程操作以及反馈
        new AccountManageServiceImpl(this).deleteAccount(userInfo.getUserId());
    }

    /**
     * 删除账户的回调函数
     *
     * @param result 是否删除成功
     */
    public void deleteAccountResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("删除成功");
            alert.setContentText("删除账户成功");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("删除失败");
            alert.setContentText("删除账户失败");
            alert.showAndWait();
        }
        //delete the selected item from the tableView
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        tempTable.getItems().remove(userInfo);
        table.refresh();
    }

    /**
     * 提交修改按钮的事件处理函数
     *
     * @param event
     */
    @FXML
    void editAccount(ActionEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        userInfo.setUserId(selectedUserId_TextField.getText());
        userInfo.setOwner(selectedOwner_TextField.getText());
        userInfo.setAccountType(selectedAccountType_ChoiceBox.getValue());
        //需要多线程操作以及反馈
        new AccountManageServiceImpl(this).editAccount(userInfo);
    }

    /**
     * 修改账户的回调函数
     *
     * @param result 是否修改成功
     */
    public void editAccountResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改成功");
            alert.setContentText("修改账户信息成功");
            alert.showAndWait();
            //刷新表格
            searchAccount(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改失败");
            alert.setContentText("修改账户信息失败");
            alert.showAndWait();
        }
    }

    /**
     * 重置密码按钮的事件处理函数
     *
     * @param event
     */
    @FXML
    void resetPassword(ActionEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        userInfo.setPassword("123456");
        //需要多线程操作,以及反馈
        new AccountManageServiceImpl(this).editAccount(userInfo);
    }

    /**
     * 重置密码的回调函数
     *
     * @param result 是否重置成功
     */
    void resetPasswordResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("重置密码成功");
            alert.setContentText("重置密码成功");
            alert.showAndWait();
            //刷新表格
            searchAccount(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("重置密码失败");
            alert.setContentText("重置密码失败,请重试");
            alert.showAndWait();
        }
    }

    /**
     * 添加账户,用户点击添加账户按钮后触发此函数
     *
     * @param event
     */
    @FXML
    void addAccount(ActionEvent event) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(addUserId_TextField.getText());
        userInfo.setOwner(addOwner_TextField.getText());
        userInfo.setPassword(addPassword_TextField.getText());
        userInfo.setAccountType(addAccountType_ChoiceBox.getValue());
        //密码中不能包含空格且密码不能为空，若新建用户的密码不符合规则就提示用户重新输入
        if (userInfo.getPassword().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("密码不能包含空格");
            alert.setContentText("请重新输入密码");
            alert.showAndWait();
        } else if (userInfo.getPassword().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("密码不能为空");
            alert.setContentText("请重新输入密码");
            alert.showAndWait();
        } else {
            new AccountManageServiceImpl(this).addAccount(userInfo);
        }
    }

    /**
     * 添加账户的回调函数
     *
     * @param result 是否添加成功
     */
    public void addAccountResult_Callback(boolean result) {
        Platform.runLater(() -> {
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("添加账户成功");
                alert.showAndWait();
                //刷新表格
                searchAccount(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("添加账户失败");
                alert.showAndWait();
            }
        });
    }

    /**
     * 初始化函数，在加载“账号管理界面”并获得本Controller的实例时，会执行
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //将表格中的列与对象属性绑定
        userId_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("userId"));
        accountType_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("accountType"));
        password_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("password"));
        owner_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("owner"));
        //创建一个存储账号类型的ArrayList并将其转换为ObservableList，用于账户类型下拉框的显示
        ArrayList<String> accountTypeList = new ArrayList<>();
        accountTypeList.add("技术操作员");
        accountTypeList.add("技术管理员");
        accountTypeList.add("厂长");
        //create ObservableList from accountTypeList，and make it the items of the ChoiceBox
        ObservableList<String> accountTypeObservableList = FXCollections.observableArrayList(accountTypeList);
        selectedAccountType_ChoiceBox.setItems(accountTypeObservableList);
        addAccountType_ChoiceBox.setItems(accountTypeObservableList);
    }

}

