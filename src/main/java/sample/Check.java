package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import util.ImageCatch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Check extends Application {

    private Main mainApp;

    public Check(Main mainApp){
        this.mainApp = mainApp;
    }
    public Check(){}

    public void start(Stage checkStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("checkImg.fxml"));
        checkStage.setTitle("检测界面");
        checkStage.setScene(new Scene(root, 600, 475));

        checkStage.show();

//        Image image = new Image("file:D:\\JAVA\\workspace\\JavaFX\\src\\img\\2.jpg");
//        ImageView imageShow = (ImageView) root.lookup("#imageShow");
//        imageShow.setImage(image);

        Timer timer = new Timer(); // 先new一个定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if(mainApp.flag){
//                            try {
//                                FileInputStream is = new FileInputStream("D:\\JAVA\\workspace\\JavaFX\\src\\img\\1.jpg");
//                                ImageCatch imageCatch = new ImageCatch();
//                                if (imageCatch.bytesImage != null){
//                                    Image image = new Image(imageCatch.bytesImage);
//                                    ImageView imageShow = (ImageView) root.lookup("#imageShow");
//                                    imageShow.setImage(image);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            Image image = new Image("file:D:\\JAVA\\workspace\\JavaFX\\src\\img\\1.jpg");
                            ImageView imageShow = (ImageView) root.lookup("#imageShow");
                            imageShow.setImage(image);
                        }
                        else{
                            Image image = new Image("file:D:\\JAVA\\workspace\\JavaFX\\src\\img\\2.jpg");
                            ImageView imageShow = (ImageView) root.lookup("#imageShow");
                            imageShow.setImage(image);
                        }
                    }
                });
            }
        },100,500);
    }
}
