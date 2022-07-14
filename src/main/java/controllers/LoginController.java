package controllers;

import app.MyApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.impl.LoginServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @description: 登录界面controller
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class LoginController implements Initializable {
    @FXML
    private Label loggingIn;

    private MyApplication myApplication;
    //    以下每个属性都代表界面上的一个组件，在使用时不需要实例化（new），因为注脚@FXML已经帮我们做了这件事了
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

    @FXML
    private AnchorPane loginAnchorPane;

    /**
     * 事件处理函数，本函数绑定在“登录”按钮上，当按钮被点击时触发此函数。
     * （关于如何绑定，在图形化拖拽工具右侧边栏，code选项中的“on Action”，填入事件处理函数的名称即可；也可查看本项目文件“login.fxml”文件第44行，有onAction="#login"字样）
     *
     * @param event
     */
    @FXML
    void login(ActionEvent event) {
//      获取用户输入的账号密码
        String userId = userIdTextField.getText();
        String password = userPasswordTextField.getText();
        String accountType = accountTypeChoiceBox.getValue();
        loggingIn.setVisible(true);

        ProgressIndicator p1 = new ProgressIndicator();

        loginAnchorPane.getChildren().add(p1);
        p1.setLayoutY(300);
        p1.setLayoutX(600);
        new LoginServiceImpl(this).login(userId, password, accountType);
    }

    /**
     * @param loginResult 登录结果，true表示登录成功，false表示登录失败
     * @param errCode     0代表登录成功,此时accountType参数为用户登录系统的意向类型；
     *                    1代表账号密码错误；此时accountType参数无效
     *                    2代表账号类型错误，此时accountType参数为用户可登录的最高账户类型；
     *                    3代表未知错误，无效
     * @param accountType
     */
    public void loginResult_Callback(boolean loginResult, int errCode, String accountType) {
        if (errCode == 0) {
            switch (accountType) {
                case "技术操作员":
                    //跳转到技术操作员界面
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.gotoProductDetectPage();
                        }
                    });
                    break;
                case "技术管理员":
                    //跳转到技术管理员界面
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            myApplication.gotoTechPage();
                        }
                    });
                    break;
                case "厂长":
                    //跳转到厂长界面
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
                    alert.setTitle("提示");
                    alert.setHeaderText("登录失败");
                    switch (errCode) {
                        case 1:
                            //提示登录失败，账号密码错误
                            alert.setContentText("账号或密码错误");
                            alert.showAndWait();
                            break;
                        case 2:
                            //提示登录失败，账户类型有误

                            alert.setContentText("选择的账户类型有误，您能选择的最高账户类型为" + accountType);
                            alert.showAndWait();
                            break;
                        case 3:
                            //提示登录失败，发生未知错误
                            alert.setContentText("数据库操作时发生未知错误");
                            alert.showAndWait();
                            break;
                        default:
                            //提示登录失败，发生未知错误
                            alert.setContentText("发生未知错误");
                            alert.showAndWait();
                            break;
                    }
                }
            });

        }
    }


    /**
     * 保存主控制类实例myApplication，以便登录成功后调用myApplication的方法跳转到其他页面
     *
     * @param myApplication
     */
    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountTypeChoiceBox.getItems().addAll("技术操作员", "技术管理员", "厂长");
        accountTypeChoiceBox.setValue("技术操作员");
    }
}
