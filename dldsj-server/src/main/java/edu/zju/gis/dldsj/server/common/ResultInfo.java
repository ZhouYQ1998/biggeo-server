package edu.zju.gis.dldsj.server.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jinghaoyu
 * @Date: 2020/8/21
 *
 * 封装分类查询的信息
 */
public class ResultInfo {
    /**
     * result : success
     * resultCode : 200  404  500
     * resultInfo : 成功   失败
     * total : 0   数量
     * list :
     * map : 同上
     * data : 数据
     */
    private String result;
    private int resultCode;
    private String resultInfo;
    private Object data;

    public ResultInfo() {
        this.result = "SUCCESS";
        this.resultCode = 200;

    }

    public static ResultInfo getInfo() {
        ResultInfo info = new ResultInfo();
        return info;
    }

    public static ResultInfo getFailedInfo(String result) {
        ResultInfo info = new ResultInfo();
        info.setResult("FAILED");
        info.setResultInfo(result);
        return info;
    }

    public static ResultInfo getMapInfo(Object data) {
        ResultInfo info = new ResultInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        info.setData(data);
        return info;
    }

    public static ResultInfo getInfo(Object data) {
        ResultInfo info = new ResultInfo();
        info.setData(data);
        return info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }


    public void setResultCode(int resultCode) {
        if (resultCode != 200)
            this.result = "FAILED";
        else
            this.result = "SUCCESS";
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "result='" + result + '\'' +
                ", resultCode=" + resultCode +
                ", resultInfo='" + resultInfo + '\'' +

                ", data=" + data +
                '}';
    }
}
