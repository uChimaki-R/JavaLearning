package com.pockyr.pojo;

public class Factory {
    private String JNO;
    private String JNAME;
    private String JCITY;

    public Factory() {
    }

    public Factory(String JNO, String JNAME, String JCITY) {
        this.JNO = JNO;
        this.JNAME = JNAME;
        this.JCITY = JCITY;
    }

    public String getJNO() {
        return JNO;
    }

    public void setJNO(String JNO) {
        this.JNO = JNO;
    }

    public String getJNAME() {
        return JNAME;
    }

    public void setJNAME(String JNAME) {
        this.JNAME = JNAME;
    }

    public String getJCITY() {
        return JCITY;
    }

    public void setJCITY(String JCITY) {
        this.JCITY = JCITY;
    }

    @Override
    public String toString() {
        return "Factory{" +
                "JNO='" + JNO + '\'' +
                ", JNAME='" + JNAME + '\'' +
                ", JCITY='" + JCITY + '\'' +
                '}';
    }
}
