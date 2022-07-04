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
 * @description: �˻��������controller
 * @author: ����
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

    //�˺�����ɸѡһ�������
    @FXML
    private TextField userId_textFeild;

    @FXML
    private TextField owner_textFeild;

    @FXML
    private Button search_button;

    //��񲿷ֵ����
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


    //����ǰѡ���˻���һ�������

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


    //����˻�һ�������
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
     * �ص����������ڸ�����������ڱ���е���ʾ
     *
     * @param userInfoList
     */
    public void searchAccount_Callback(ObservableList<UserInfo> userInfoList) {
        table.setItems(userInfoList);
        table.refresh();
    }

    /**
     * �����������
     *
     * @param event
     */
    @FXML
    void cleanTextField(ActionEvent event) {
        userId_textFeild.setText("");
        owner_textFeild.setText("");
    }


    /**
     * ��ȡ������ı���е��У���������Ӧ���˻���Ϣ��ʾ�ڡ���ǰѡ���˻���һ����
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
     * ɾ���˻���ť���¼�������
     *
     * @param event
     */
    @FXML
    void deleteAccount(ActionEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        //��Ҫ���̲߳����Լ�����
        new AccountManageServiceImpl(this).deleteAccount(userInfo.getUserId());
    }

    /**
     * ɾ���˻��Ļص�����
     *
     * @param result �Ƿ�ɾ���ɹ�
     */
    public void deleteAccountResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("ɾ���ɹ�");
            alert.setContentText("ɾ���˻��ɹ�");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("ɾ��ʧ��");
            alert.setContentText("ɾ���˻�ʧ��");
            alert.showAndWait();
        }
        //delete the selected item from the tableView
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        tempTable.getItems().remove(userInfo);
        table.refresh();
    }

    /**
     * �ύ�޸İ�ť���¼�������
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
        //��Ҫ���̲߳����Լ�����
        new AccountManageServiceImpl(this).editAccount(userInfo);
    }

    /**
     * �޸��˻��Ļص�����
     *
     * @param result �Ƿ��޸ĳɹ�
     */
    public void editAccountResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("�޸ĳɹ�");
            alert.setContentText("�޸��˻���Ϣ�ɹ�");
            alert.showAndWait();
            //ˢ�±��
            searchAccount(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("�޸�ʧ��");
            alert.setContentText("�޸��˻���Ϣʧ��");
            alert.showAndWait();
        }
    }

    /**
     * �������밴ť���¼�������
     *
     * @param event
     */
    @FXML
    void resetPassword(ActionEvent event) {
        TableView<UserInfo> tempTable = table;
        UserInfo userInfo = tempTable.getSelectionModel().getSelectedItem();
        userInfo.setPassword("123456");
        //��Ҫ���̲߳���,�Լ�����
        new AccountManageServiceImpl(this).editAccount(userInfo);
    }

    /**
     * ��������Ļص�����
     *
     * @param result �Ƿ����óɹ�
     */
    void resetPasswordResult_Callback(boolean result) {
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("��������ɹ�");
            alert.setContentText("��������ɹ�");
            alert.showAndWait();
            //ˢ�±��
            searchAccount(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("��������ʧ��");
            alert.setContentText("��������ʧ��,������");
            alert.showAndWait();
        }
    }

    /**
     * ����˻�,�û��������˻���ť�󴥷��˺���
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
        //�����в��ܰ����ո������벻��Ϊ�գ����½��û������벻���Ϲ������ʾ�û���������
        if (userInfo.getPassword().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("����");
            alert.setHeaderText("���벻�ܰ����ո�");
            alert.setContentText("��������������");
            alert.showAndWait();
        } else if (userInfo.getPassword().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("����");
            alert.setHeaderText("���벻��Ϊ��");
            alert.setContentText("��������������");
            alert.showAndWait();
        } else {
            new AccountManageServiceImpl(this).addAccount(userInfo);
        }
    }

    /**
     * ����˻��Ļص�����
     *
     * @param result �Ƿ���ӳɹ�
     */
    public void addAccountResult_Callback(boolean result) {
        Platform.runLater(() -> {
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("��ʾ");
                alert.setHeaderText("����˻��ɹ�");
                alert.showAndWait();
                //ˢ�±��
                searchAccount(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("����");
                alert.setHeaderText("����˻�ʧ��");
                alert.showAndWait();
            }
        });
    }

    /**
     * ��ʼ���������ڼ��ء��˺Ź�����桱����ñ�Controller��ʵ��ʱ����ִ��
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //������е�����������԰�
        userId_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("userId"));
        accountType_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("accountType"));
        password_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("password"));
        owner_tableCol.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("owner"));
        //����һ���洢�˺����͵�ArrayList������ת��ΪObservableList�������˻��������������ʾ
        ArrayList<String> accountTypeList = new ArrayList<>();
        accountTypeList.add("��������Ա");
        accountTypeList.add("��������Ա");
        accountTypeList.add("����");
        //create ObservableList from accountTypeList��and make it the items of the ChoiceBox
        ObservableList<String> accountTypeObservableList = FXCollections.observableArrayList(accountTypeList);
        selectedAccountType_ChoiceBox.setItems(accountTypeObservableList);
        addAccountType_ChoiceBox.setItems(accountTypeObservableList);
    }

}

