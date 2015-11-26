package com.innofarm.model;

/**
 * cattleFileRequest
 */
public class CattleFileModel {
    private String barnName; //牛舍
    private String breedSt; //繁育状态
    private String calvingNo; //胎次
    private String cattleAge; //月龄
    private String cattleId; //牛ID
    private String cattleNo; //牛号
    private String cattleSex; //牛性别
    private String cattlebrt; //牛出生日期
    private String varieties; //品种
    private String source; //来源
    private String father; //父系
    private String mother; //母系


    public String getBarnName() {
        return barnName;
    }

    public String getBreedSt() {
        return breedSt;
    }

    public String getCattleAge() {
        return cattleAge;
    }

    public String getCattleId() {
        return cattleId;
    }

    public String getCattleSex() {
        return cattleSex;
    }

    public String getCattlebrt() {
        return cattlebrt;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSource() {
        return source;
    }

    public String getVarieties() {
        return varieties;
    }

    public String getCattleNo() {
        return cattleNo;
    }

    public String getCalvingNo() {
        return calvingNo;
    }


    @Override
    public String toString() {
        return "CattleFileModel{" +
                "barnName='" + barnName + '\'' +
                ", breedSt='" + breedSt + '\'' +
                ", calvingNo='" + calvingNo + '\'' +
                ", cattleAge='" + cattleAge + '\'' +
                ", cattleId='" + cattleId + '\'' +
                ", cattleNo='" + cattleNo + '\'' +
                ", cattleSex='" + cattleSex + '\'' +
                ", cattlebrt='" + cattlebrt + '\'' +
                ", varieties='" + varieties + '\'' +
                ", source='" + source + '\'' +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                '}';
    }
}
