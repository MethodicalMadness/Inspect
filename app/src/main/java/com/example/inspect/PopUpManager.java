package com.example.inspect;

public class PopUpManager {
    private String heading;
    private String message;
    private String positiveBtn;
    private String negativeBtn;

    public PopUpManager(String heading, String message, String positiveBtn, String negativeBtn) {
        this.heading = heading;
        this.message = message;
        this.positiveBtn = positiveBtn;
        this.negativeBtn = negativeBtn;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositiveBtn() {
        return positiveBtn;
    }

    public void setPositiveBtn(String positiveBtn) {
        this.positiveBtn = positiveBtn;
    }

    public String getNegativeBtn() {
        return negativeBtn;
    }

    public void setNegativeBtn(String negativeBtn) {
        this.negativeBtn = negativeBtn;
    }
}
