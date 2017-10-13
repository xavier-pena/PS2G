package com.example.xadp5.ps2g_app;

/**
 * Created by Ayr D on 8/17/2017.
 */

public class projectInfo {
    private String projectDescription;
    private int percentComplete;
    private int amountLeft;


    public void setProjectDescription(){}
    public void setPercentComplete(int i){

        percentComplete=i;
    }
    public void setAmountLeft(){}
    public String getProjectDescription(){

        return projectDescription;
    }


    public int getPercentComplete() {
        return percentComplete;
    }

    public int getAmountLeft() {
        return amountLeft;
    }
}
