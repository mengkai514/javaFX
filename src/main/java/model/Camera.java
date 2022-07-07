package model;

public class Camera {
    private String height;
    private String width;
    private String acquisitionRate;
    private String exTime;
    private String conveyorSpeed;

    public Camera() {
    }

    public Camera(String height, String width, String acquisitionRate, String exTime, String conveyorSpeed) {
        this.height = height;
        this.width = width;
        this.acquisitionRate = acquisitionRate;
        this.exTime = exTime;
        this.conveyorSpeed = conveyorSpeed;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", acquisitionRate='" + acquisitionRate + '\'' +
                ", exTime='" + exTime + '\'' +
                ", conveyorSpeed='" + conveyorSpeed + '\'' +
                '}';
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAcquisitionRate() {
        return acquisitionRate;
    }

    public void setAcquisitionRate(String acquisitionRate) {
        this.acquisitionRate = acquisitionRate;
    }

    public String getExTime() {
        return exTime;
    }

    public void setExTime(String exTime) {
        this.exTime = exTime;
    }

    public String getConveyorSpeed() {
        return conveyorSpeed;
    }

    public void setConveyorSpeed(String conveyorSpeed) {
        this.conveyorSpeed = conveyorSpeed;
    }
}
