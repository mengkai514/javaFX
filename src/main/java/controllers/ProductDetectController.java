package controllers;

import app.MyApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import service.ConveyorService;
import service.impl.ConveyorServiceImpl;
import sun.misc.BASE64Decoder;
import utils.SocketListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * @description: 产品检测界面的controller
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class ProductDetectController implements Initializable {

    @FXML
    private TextField numberOfDefectTextField;


    @FXML
    private Button clearProductPlateBuuton1;

    @FXML
    private Button startButton;

    @FXML
    private Button clearProductPlateBuuton;

    @FXML
    private TextField cameraExposureTimeTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button stopbutton;

    @FXML
    private TextField conveyorSpeedTextField;

    @FXML
    private TextField numberOfDetectedTextField;

    @FXML
    private TextField CameraResolutionTextField;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField numberOfGoodProductTextField;

    @FXML
    private Circle pinAskewCircle;

    @FXML
    private Circle pinGlueCircle;

    @FXML
    private TextField defectRateTextField;

    @FXML
    private Circle glueOutCircle;
    //已检测数量
    private int numberOfDetected = 0;
    //缺陷数量
    private int numberOfDefect = 0;
    //缺陷率
    private double defectRate = 0;
    //良品数量
    private int numberOfGoodProduct = 0;

    private MyApplication myApplication;

    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @FXML
    void startConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.startConveyor()) {
            //弹窗提示传送带启动成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带启动成功");
            alert.setContentText("传送带启动成功");
            alert.showAndWait();
            //            改变状态标签的颜色为绿色
            statusCircle.setFill(javafx.scene.paint.Color.GREEN);
            statusLabel.setText("正在运行...");
        } else {
            //弹窗提示传送带启动失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带启动失败");
            alert.setContentText("传送带启动失败");
            alert.showAndWait();
            //            改变状态标签的颜色为红色
            statusLabel.setText("未运行");
            statusCircle.setFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    void stopConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.stopConveyor()) {
            //弹窗提示传送带停止成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带停止成功");
            alert.setContentText("传送带停止成功");
            alert.showAndWait();

            //            改变状态标签的颜色为红色
            statusCircle.setFill(Color.RED);
            statusLabel.setText("未运行");
        } else {
            //弹窗提示传送带启动失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带停止失败");
            alert.setContentText("传送带停止失败");
            alert.showAndWait();
            //            改变状态标签的颜色为绿色
            statusCircle.setFill(Color.GREEN);
            statusLabel.setText("正在运行...");
        }
    }

    @FXML
    void resetPlate(ActionEvent event) {

    }

    @FXML
    void resetData(ActionEvent event) {
        numberOfDetectedTextField.setText("0");
        numberOfDefectTextField.setText("0");
        defectRateTextField.setText("0");
        numberOfGoodProductTextField.setText("0");

    }

    public void setImageByBase64(String base64) {
        //将base64解码为jpg图片,并显示在imageView上
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
//          打开文件输出流
            out = new FileOutputStream("src\\main\\resources\\image\\result.jpg");
            //去掉前缀data:image/jpeg;base64,
            base64 = base64.substring(base64.indexOf(",", 1) + 1, base64.length());
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            //处理异常值
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            //写入文件
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //从刚刚存入的文件中创建Image对象
        Image image = new Image("file:src/main/resources/image/result.jpg");
        //将image对象显示在imageView上
        imageView.setImage(image);
    }

    /**
     * 从url获取图片并设置在imageView上，本地url示例：file:src/main/resources/image/result.jpg
     * @param imagePath
     */
    public void setImageByPath(String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }

    /**
     * 回调函数，用于设置结果在页面上显示
     * @param imageBase64 图片的base64编码
     * @param isPinAskew 针脚是否歪斜
     * @param isPinGlue 针脚是否黏胶
     * @param isGlueOut 是否溢胶
     */
    public void setDetectResult(String imageBase64, boolean isPinAskew, boolean isPinGlue, boolean isGlueOut) {
        //更新图片
        setImageByBase64(imageBase64);
        //更新检测结果
        //针脚歪斜
        if (isPinAskew) {
            pinAskewCircle.setFill(Color.GREEN);
        } else {
            pinAskewCircle.setFill(Color.RED);
        }
        //针脚粘胶
        if (isPinGlue) {
            pinGlueCircle.setFill(Color.GREEN);
        } else {
            pinGlueCircle.setFill(Color.RED);
        }
        //溢胶
        if (isGlueOut) {
            glueOutCircle.setFill(Color.GREEN);
        } else {
            glueOutCircle.setFill(Color.RED);
        }

//        更新批次统计信息
        numberOfDetectedTextField.setText(String.valueOf(++numberOfDetected));
        if (isPinAskew == false || isPinGlue == false || isGlueOut == false) {
            numberOfDefectTextField.setText(String.valueOf(++numberOfDefect));
        }

        defectRate = ((double) numberOfDefect) / numberOfDetected;

        defectRateTextField.setText(String.valueOf(defectRate * 100).substring(0, 4) + "%");

        numberOfGoodProduct = numberOfDetected - numberOfDefect;
        numberOfGoodProductTextField.setText(String.valueOf(numberOfGoodProduct));


    }

    /**
     * 重置标记缺陷类型的圆圈的颜色
     */
    public void resetDefectCircleColor() {
        pinAskewCircle.setFill(Color.GREEN);
        pinGlueCircle.setFill(Color.GREEN);
        glueOutCircle.setFill(Color.GREEN);
    }
    /**
     * 在加载本页面时，并获取本Controller的示例时，会调用此方法进行初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //创建socket监听线程实例
        SocketListener socketListener = new SocketListener();
        //传输本controller1的实例，以便在监听线程中调用回调函数更新界面
        socketListener.setProductDetectController(this);
        //启动线程
        new Thread(socketListener).start();
    }
}
