package app;

import configs.StaticResourcesConfig;
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
 * @description: ���ƽ�����ʾ�߼�
 * @author: ����
 * @date: 2022-7-4
 */
public class MyApplication extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("�����ȱ�ݼ��ϵͳ");
        //���ص�¼����
        gotoLogin();
        //���ÿɸı䴰�ڴ�С
        stage.setResizable(true);
        //չʾ�ҽ���
        stage.show();
    }

    /**
     * ��ת����¼����
     */
    public void gotoLogin() {
        try {
            LoginController loginController = (LoginController) replaceSceneContent(StaticResourcesConfig.LOGIN_VIEW_PATH);
            //��myApplicationʵ������loginController���Ա��ڵ�¼�ɹ�����loginController����gotoMain()������ʾ������
            loginController.setApp(this);
        } catch (Exception ex) {
            System.out.println("��¼������ش���");
        }
    }

    /**
     * ��ת��������
     */
    public void gotoTechPage() {
        try {
            TechFrameController techFrameController = (TechFrameController) replaceSceneContent(StaticResourcesConfig.MAIN_VIEW_PATH);
            techFrameController.setApp(this);
        } catch (Exception ex) {
            System.out.println("��������Ա������ش���");
        }
    }

    public void gotoProductDetectPage() {
        try {
            ProductDetectController productDetectController = (ProductDetectController) replaceSceneContent(StaticResourcesConfig.PRODUCTDETECT_VIEW_PATH);
            productDetectController.setApp(this);
        } catch (Exception ex) {
            System.out.println("��������Ա������ش���");
        }
    }


    public void gotoMasterPage() {
    }

    /**
     * �û�Scene����ȡ��Scence�󶨵�Controllerʵ��
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
//            ׼������Scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        //����fxml�󶨵�controller
        return (Initializable) loader.getController();
    }

    /**
     * �������main
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
