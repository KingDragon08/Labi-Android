package cn.wildfire.chat.kit.net.base;

/**
 * Created by imndx on 2017/12/16.
 */

/**
 * 用来表示result的状态，上层基本不用关注
 */
public class StatusResult {

    private String msg;
    private int status;

    public boolean isSuccess() {
        return status == 0;
    }

    public String getMsg() {
        return msg;
    }

    public String getMessage() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(int code) {
        this.status = code;
    }
}
