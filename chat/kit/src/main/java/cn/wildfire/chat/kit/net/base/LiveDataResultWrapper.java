package cn.wildfire.chat.kit.net.base;

/**
 * Created by hp on 2020/2/12.
 * 供LiveData使用的包装类，如果T有值，则忽略code和msg即可，只有当T没有值的时候才去取code和msg
 */
public class LiveDataResultWrapper<T> {
    //实际数据
    public T data;
    //code
    public int code;
    //message
    public String message;

    public LiveDataResultWrapper() {
    }

    public LiveDataResultWrapper(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public LiveDataResultWrapper(T data) {
        this.data = data;
    }

    public LiveDataResultWrapper(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
