package com.lwy.bootws.bean;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Result {
    private int code;
    private String desc;
    private Object data;

    public Result() {

    }

    public Result(int code, String desc, Object data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public static Result sussess() {
        return new Result(0, "操作成功", null);
    }
    public static Result sussess(Object data) {
        return new Result(0, "操作成功", data);
    }


    public static Result fail() {
        return new Result(1, "操作失败", null);
    }

    public static void main(String[] args) {
        Result result = new Result();
        Result result2 = Result.sussess(); //.setData(new Result().fail());
        System.out.println(JSON.toJSONString(result));
    }
}
