package model;

import lombok.Data;

@Data
public class Camera {
    private String height;
    private String width;
    private String acquisitionRate;
    private String exTime;
    private String conveyorSpeed;
    private String cameraOption;
    private String cameraOrientation;

    public Camera() {
    }

    public Camera(String height, String width, String acquisitionRate, String exTime, String conveyorSpeed, String cameraOption, String cameraOrientation) {
        this.height = height;
        this.width = width;
        this.acquisitionRate = acquisitionRate;
        this.exTime = exTime;
        this.conveyorSpeed = conveyorSpeed;
        this.cameraOption = cameraOption;
        this.cameraOrientation = cameraOrientation;
    }


}
