package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sun.misc.BASE64Decoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * @author ：黄涛
 * @description：检测结果的照片弹窗界面的controller
 * @date ：2022/7/7 15:20
 */
public class ResultImageViewController implements Initializable {

    @FXML
    private Circle pinAskewCircle;

    @FXML
    private Circle pinGlueCircle;

    @FXML
    private Circle glueOutCircle;

    @FXML
    private ImageView imageView;

    /**
     * 传入jpg图片的base64编码，将其显示在界面的ImageView中
     * @param base64
     */
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

    /**
     * 从url获取图片并设置在imageView上，本地url示例：file:src/main/resources/image/result.jpg
     * @param imagePath
     */
    public void setImageByPath(String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }

    /**
     * @description：将检测结果展示在界面上
     * @param imageBase64 检测结果图片的base64编码
     * @param isPinAskew 针脚是否偏斜
     * @param isPinGlue 针脚是否黏胶
     * @param isGlueOut 是否又有胶水溢出
     */
    public void setResult(String imageBase64, boolean isPinAskew, boolean isPinGlue, boolean isGlueOut){
        //更新图片
        setImageByBase64(imageBase64);
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

