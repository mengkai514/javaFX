package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeviceParamsView extends Application {
    Controller controller = new Controller();
    public void start(Stage deviceParamsStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("deviceParams.fxml"));
        deviceParamsStage.setTitle("设备参数");
        deviceParamsStage.setScene(new Scene(root, 800, 600));
        deviceParamsStage.show();
        TextField cameraHeight = (TextField) root.lookup("#cameraHeight");
        TextField cameraWidth = (TextField) root.lookup("#cameraWidth");
        TextField acquisitionFrameRate = (TextField) root.lookup("#acquisitionFrameRate");
        TextField exposureTime = (TextField) root.lookup("#exposureTime");
        TextField conveyorSpeed = (TextField) root.lookup("#conveyorSpeed");

        cameraHeight.setText(controller.paramsList.get(2));
        cameraWidth.setText(controller.paramsList.get(4));
        exposureTime.setText(controller.paramsList.get(1));
        acquisitionFrameRate.setText(controller.paramsList.get(0));
        conveyorSpeed.setText(controller.paramsList.get(3));
//        System.out.println(controller.paramsList.get(1));

    }
}
