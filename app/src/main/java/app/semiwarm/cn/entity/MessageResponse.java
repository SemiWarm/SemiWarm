package app.semiwarm.cn.entity;

import java.io.Serializable;

/**
 * 请求短信接口的响应实体
 * Created by alibct on 2017/3/11.
 */

public class MessageResponse implements Serializable{
    private Integer code;
    private String msg;
    private Integer count;
    private Double fee;
    private String unit;
    private String mobile;
    private Long sid;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", fee=" + fee +
                ", unit='" + unit + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sid=" + sid +
                '}';
    }
}
