package cn.wildfire.chat.app.redpacket;

import android.os.AsyncTask;
import android.os.Build;

public class s {
    public static <Params, Progress, Result> void a(AsyncTask<Params, Progress, Result> paramAsyncTask, Params... paramVarArgs) {
        if (Build.VERSION.SDK_INT >= 11) {
            paramAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramVarArgs);
        } else {
            paramAsyncTask.execute(paramVarArgs);
        }
    }
}