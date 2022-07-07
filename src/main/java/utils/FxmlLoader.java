package utils;

import app.MyApplication;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author ：黄涛
 * @description：fxml文件加载器
 * @date ：2022/7/6 21:53
 */
public class FxmlLoader {
    /**
     *
     * @param fxmlPath
     * @return ArrayList<Object> 包含fxml对应的controller和fxml对应的父组件对象
     */
    public static ArrayList<Object> addFxml(String fxmlPath) {
        ArrayList<Object> objectsToReturn = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader();

        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MyApplication.class.getResource(fxmlPath));


        InputStream in = MyApplication.class.getResourceAsStream(fxmlPath);

        try {
            AnchorPane page = (AnchorPane) loader.load(in);
            objectsToReturn.add((Initializable) loader.getController());
            objectsToReturn.add(page);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            return objectsToReturn;
        }

    }}
