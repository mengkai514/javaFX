package controllers;

import app.MyApplication;
import configs.StaticResourcesConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @description: 产品检测界面里，每个结果查看小矩形的controller
 * @author: 黄涛
 * @date: 2022-7-7 12:10
 */
public class ResultViewController implements Initializable {

    @FXML
    private Label digitalTubeIndexLabel;

    @FXML
    private Circle pinAskewCircle;

    @FXML
    private Circle pinGlueCircle;

    @FXML
    private Circle glueOutCircle;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imageView;

    //检测图片的base64编码，只保存，不显示
    private String base64;
    private boolean isPinAskew;
    private boolean isPinGlue;
    private boolean isGlueOut;


    @FXML
    void onMouseClicked(MouseEvent event) {
        //创建另外一个stage
        Stage newStage = new Stage();
        newStage.setTitle("检测结果图片");
        newStage.setResizable(true);
        newStage.setAlwaysOnTop(true);
        newStage.setX(300);
        newStage.setY(100);
        Scene scene =getResultImageViewScene(StaticResourcesConfig.RESULTIMAGE_VIEW_PATH);
        newStage.setScene(scene);
        newStage.sizeToScene();
        newStage.show();
    }

    /**
     * @description: 设置结果，四个结果均会被保存在本对象的属性中，以便用于传递给ResultImageView用于弹窗显示；本界面除了图片不展示，其他均展示。
     * @param base64
     * @param isPinAskew
     * @param isPinGlue
     * @param isGlueOut
     */
    public void setDetectResult(String base64,boolean isPinAskew, boolean isPinGlue, boolean isGlueOut) {
        //保存传入的数据，有可能会传递给ResultImageView用于弹窗显示
        this.base64= base64;
        this.isPinAskew = isPinAskew;
        this.isPinGlue = isPinGlue;
        this.isGlueOut = isGlueOut;

        //设置图片
        this.setImageByBase64(base64);
        //设置检测结果
        //针脚歪斜
        if (isPinAskew) {
            pinAskewCircle.setFill(Color.RED);
        } else {
            pinAskewCircle.setFill(Color.GREEN);
        }
        //针脚粘胶
        if (isPinGlue) {
            pinGlueCircle.setFill(Color.RED);
        } else {
            pinGlueCircle.setFill(Color.GREEN);
        }
        //溢胶
        if (isGlueOut) {
            glueOutCircle.setFill(Color.RED);
        } else {
            glueOutCircle.setFill(Color.GREEN);
        }
    }


    /**
     * @description: 设置左上角的编号
     * @param text
     */
    public void setIndexLabel(String text){
        digitalTubeIndexLabel.setText(text);
    }


    /**
     * @description: 设置当前resultView的边框为选中状态
     */
    public void setSelectedBorderStyle() {
        Border border = new Border(new BorderStroke(Color.rgb(255,0,0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4)));
        anchorPane.setBorder(border);
    }


    /**
     * @description: 设置当前resultView的边框为默认状态
     */
    public void setDefaultBorderStyle() {
        Border border = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
        anchorPane.setBorder(border);
    }

    /**
     * @description: 给stage添加一个sence
     * @param fxmlPath
     * @return
     */
    private Scene getResultImageViewScene(String fxmlPath ) {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MyApplication.class.getResource(fxmlPath));
        InputStream in = MyApplication.class.getResourceAsStream(fxmlPath);
        Parent root = null;
        Scene scene = null;
        try {
            root = loader.load(in);
            ResultImageViewController resultImageViewController = (ResultImageViewController)loader.getController();
            //给ResultImageView界面设置数据，用于显示
            if(base64!=null)
                resultImageViewController.setResult(base64,isPinAskew,isPinGlue,isGlueOut);
        // 准备好新Scene
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭流
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //返回新Scene
            return scene;
        }
    }

    private void setImageByBase64(String base64) {
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
