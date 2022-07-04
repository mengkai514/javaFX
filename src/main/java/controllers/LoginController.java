package controllers;

import app.MyApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import service.LoginService;
import service.impl.LoginServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @description: ��¼����controller
 * @author: ����
 * @date: 2022-7-4
 */
public class LoginController implements Initializable {

    private MyApplication myApplication;
    //    ����ÿ�����Զ���������ϵ�һ���������ʹ��ʱ����Ҫʵ������new������Ϊע��@FXML�Ѿ������������������
    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField userPasswordTextField;


    @FXML
    private ChoiceBox<String> accountTypeChoiceBox;

    @FXML
    private FlowPane loginFlowPane;

    @FXML
    private Button loginButton;

    /**
     * �¼������������������ڡ���¼����ť�ϣ�����ť�����ʱ�����˺�����
     * ��������ΰ󶨣���ͼ�λ���ק�����Ҳ������codeѡ���еġ�on Action���������¼������������Ƽ��ɣ�Ҳ�ɲ鿴����Ŀ�ļ���login.fxml���ļ���44�У���onAction="#login"������
     *
     * @param event
     */
    @FXML
    void login(ActionEvent event) {

//      ��ȡ�û�������˺�����
        String userId = userIdTextField.getText();
        String password = userPasswordTextField.getText();
        String accountType = accountTypeChoiceBox.getValue();
        new LoginServiceImpl(this).login(userId, password, accountType);
    }

    /**
     * @param loginResult ��¼�����true��ʾ��¼�ɹ���false��ʾ��¼ʧ��
     * @param errCode     0�����¼�ɹ�,��ʱaccountType����Ϊ�û���¼ϵͳ���������ͣ�
     *                    1�����˺�������󣻴�ʱaccountType������Ч
     *                    2�����˺����ʹ��󣬴�ʱaccountType����Ϊ�û��ɵ�¼������˻����ͣ�
     *                    3����δ֪������Ч
     * @param accountType
     */
    public void loginResult_Callback(boolean loginResult, int errCode, String accountType) {
        if (errCode == 0) {
            switch (accountType) {
                case "��������Ա":
                    //��ת����������Ա����
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.gotoProductDetectPage();
                        }
                    });
                    break;
                case "��������Ա":
                    //��ת����������Ա����
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.gotoTechPage();
                        }
                    });
                    break;
                case "����":
                    //��ת����������
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.gotoMasterPage();
                        }
                    });
                    break;
                default:

            }

        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("��ʾ");
                    alert.setHeaderText("��¼ʧ��");
                    switch (errCode) {
                        case 1:
                            //��ʾ��¼ʧ�ܣ��˺��������
                            alert.setContentText("�˺Ż��������");
                            alert.showAndWait();
                            break;
                        case 2:
                            //��ʾ��¼ʧ�ܣ��˻���������

                            alert.setContentText("ѡ����˻�������������ѡ�������˻�����Ϊ" + accountType);
                            alert.showAndWait();
                            break;
                        case 3:
                            //��ʾ��¼ʧ�ܣ�����δ֪����
                            alert.setContentText("���ݿ����ʱ����δ֪����");
                            alert.showAndWait();
                            break;
                        default:
                            //��ʾ��¼ʧ�ܣ�����δ֪����
                            alert.setContentText("����δ֪����");
                            alert.showAndWait();
                            break;
                    }
                }
            });

        }
    }


    /**
     * ������������ʵ��myApplication���Ա��¼�ɹ������myApplication�ķ�����ת������ҳ��
     *
     * @param myApplication
     */
    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountTypeChoiceBox.getItems().addAll("��������Ա", "��������Ա", "����");
        accountTypeChoiceBox.setValue("��������Ա");
    }


}
