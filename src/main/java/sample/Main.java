package sample;

import javafx.stage.Stage;
import util.DetectProductPositionByFiberSensors;
import util.ImageCatch;


public class Main{
    static DetectProductPositionByFiberSensors detectProduct = new DetectProductPositionByFiberSensors();
    static Main mainApp = new Main();
    static ImageCatch imageCatch = new ImageCatch(mainApp);
    public static boolean flag = false;
    static Check check = new Check(mainApp);

    public void ImageCatch2(){
        new Thread(imageCatch).start();
//        flag = false;

    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                //开启UI界面
                javafx.application.Application.launch(MainUi.class);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                // 通过光纤传感器检测物品位置，看物品是否到达检测区域
                while(true){
                    boolean isArrive = detectProduct.detectProductPosition();
                    System.out.println(isArrive);
                    if (isArrive){
//                        flag = false;
                        mainApp.ImageCatch2();
                    }
                }

            }
        }).start();

    }
}
