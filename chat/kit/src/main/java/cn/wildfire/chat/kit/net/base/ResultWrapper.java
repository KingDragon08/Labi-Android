package cn.wildfire.chat.kit.net.base;

/**
 * Created by imndx on 2017/12/15.
 */

public class ResultWrapper<T> extends StatusResult {
    T data;

    public T getResult() {
        return data;
    }

    public void setResult(T data) {
        this.data = data;
    }
}
