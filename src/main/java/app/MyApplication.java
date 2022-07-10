package app;

import configs.StaticResourcesConfig;
import controllers.FactoryManagerController;
import controllers.LoginController;
import controllers.ProductDetectController;
import controllers.TechFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * @description: 控制界面显示逻辑
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class MyApplication extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("数码管缺陷检测系统");
        //加载登录界面
        gotoLogin();
        //设置可改变窗口大小
        stage.setResizable(true);
        //展示揭界面
        stage.show();
    }

    /**
     * 跳转到登录界面
     */
    public void gotoLogin() {
        try {
            LoginController loginController = (LoginController) replaceSceneContent(StaticResourcesConfig.LOGIN_VIEW_PATH);
            //将myApplication实例传给loginController，以便在登录成功后由loginController调用gotoMain()函数显示主界面
            loginController.setApp(this);
        } catch (Exception ex) {
            System.out.println("登录界面加载错误");
        }
    }

    /**
     * 跳转到主界面
     */
    public void gotoTechPage() {
        try {
            TechFrameController techFrameController = (TechFrameController) replaceSceneContent(StaticResourcesConfig.MAIN_VIEW_PATH);
            techFrameController.setApp(this);
        } catch (Exception ex) {
            System.out.println("技术管理员界面加载错误");
        }
    }

    public void gotoProductDetectPage() {
        try {
            ProductDetectController productDetectController = (ProductDetectController) replaceSceneContent(StaticResourcesConfig.PRODUCTDETECT_VIEW_PATH);
            productDetectController.setApp(this);
        } catch (Exception ex) {
            System.out.println("技术操作员界面加载错误");
        }
    }


    public void gotoMasterPage() {
        try {
            FactoryManagerController factoryManagerController = (FactoryManagerController) replaceSceneContent(StaticResourcesConfig.MASTERMAIN_VIEW_PATH);
            factoryManagerController.setApp(this);
        } catch (Exception ex) {
            System.out.println("厂长界面加载错误");
        }
    }

    /**
     * 置换Scene，获取新Scence绑定的Controller实例
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MyApplication.class.getResource(fxml));
        InputStream in = MyApplication.class.getResourceAsStream(fxml);
        try {

            Parent root = loader.load(in);
//            准备好新Scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        //返回fxml绑定的controller
        return (Initializable) loader.getController();
    }

    /**
     * 程序入口main
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
