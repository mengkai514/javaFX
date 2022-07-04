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
 * @description: ��Ʒ�������controller
 * @author: ����
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
    //�Ѽ������
    private int numberOfDetected = 0;
    //ȱ������
    private int numberOfDefect = 0;
    //ȱ����
    private double defectRate = 0;
    //��Ʒ����
    private int numberOfGoodProduct = 0;

    private MyApplication myApplication;

    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @FXML
    void startConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.startConveyor()) {
            //������ʾ���ʹ������ɹ�
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("���ʹ������ɹ�");
            alert.setContentText("���ʹ������ɹ�");
            alert.showAndWait();
            //            �ı�״̬��ǩ����ɫΪ��ɫ
            statusCircle.setFill(javafx.scene.paint.Color.GREEN);
            statusLabel.setText("��������...");
        } else {
            //������ʾ���ʹ�����ʧ��
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("���ʹ�����ʧ��");
            alert.setContentText("���ʹ�����ʧ��");
            alert.showAndWait();
            //            �ı�״̬��ǩ����ɫΪ��ɫ
            statusLabel.setText("δ����");
            statusCircle.setFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    void stopConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.stopConveyor()) {
            //������ʾ���ʹ�ֹͣ�ɹ�
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("���ʹ�ֹͣ�ɹ�");
            alert.setContentText("���ʹ�ֹͣ�ɹ�");
            alert.showAndWait();

            //            �ı�״̬��ǩ����ɫΪ��ɫ
            statusCircle.setFill(Color.RED);
            statusLabel.setText("δ����");
        } else {
            //������ʾ���ʹ�����ʧ��
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText("���ʹ�ֹͣʧ��");
            alert.setContentText("���ʹ�ֹͣʧ��");
            alert.showAndWait();
            //            �ı�״̬��ǩ����ɫΪ��ɫ
            statusCircle.setFill(Color.GREEN);
            statusLabel.setText("��������...");
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
        //��base64����ΪjpgͼƬ,����ʾ��imageView��
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
//          ���ļ������
            out = new FileOutputStream("src\\main\\resources\\image\\result.jpg");
            //ȥ��ǰ׺data:image/jpeg;base64,
            base64 = base64.substring(base64.indexOf(",", 1) + 1, base64.length());
            // Base64����
            byte[] b = decoder.decodeBuffer(base64);
            //�����쳣ֵ
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// �����쳣����
                    b[i] += 256;
                }
            }
            //д���ļ�
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
        //�Ӹոմ�����ļ��д���Image����
        Image image = new Image("file:src/main/resources/image/result.jpg");
        //��image������ʾ��imageView��
        imageView.setImage(image);
    }

    /**
     * ��url��ȡͼƬ��������imageView�ϣ�����urlʾ����file:src/main/resources/image/result.jpg
     * @param imagePath
     */
    public void setImageByPath(String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }

    /**
     * �ص��������������ý����ҳ������ʾ
     * @param imageBase64 ͼƬ��base64����
     * @param isPinAskew ����Ƿ���б
     * @param isPinGlue ����Ƿ�𤽺
     * @param isGlueOut �Ƿ��罺
     */
    public void setDetectResult(String imageBase64, boolean isPinAskew, boolean isPinGlue, boolean isGlueOut) {
        //����ͼƬ
        setImageByBase64(imageBase64);
        //���¼����
        //�����б
        if (isPinAskew) {
            pinAskewCircle.setFill(Color.GREEN);
        } else {
            pinAskewCircle.setFill(Color.RED);
        }
        //���ճ��
        if (isPinGlue) {
            pinGlueCircle.setFill(Color.GREEN);
        } else {
            pinGlueCircle.setFill(Color.RED);
        }
        //�罺
        if (isGlueOut) {
            glueOutCircle.setFill(Color.GREEN);
        } else {
            glueOutCircle.setFill(Color.RED);
        }

//        ��������ͳ����Ϣ
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
     * ���ñ��ȱ�����͵�ԲȦ����ɫ
     */
    public void resetDefectCircleColor() {
        pinAskewCircle.setFill(Color.GREEN);
        pinGlueCircle.setFill(Color.GREEN);
        glueOutCircle.setFill(Color.GREEN);
    }
    /**
     * �ڼ��ر�ҳ��ʱ������ȡ��Controller��ʾ��ʱ������ô˷������г�ʼ��
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //����socket�����߳�ʵ��
        SocketListener socketListener = new SocketListener();
        //���䱾controller1��ʵ�����Ա��ڼ����߳��е��ûص��������½���
        socketListener.setProductDetectController(this);
        //�����߳�
        new Thread(socketListener).start();
    }
}
