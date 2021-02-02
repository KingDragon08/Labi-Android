package cn.wildfire.chat.app.redpacket;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class e {
    private static final String a = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jrmf_image";

    public void a(String paramString, Bitmap paramBitmap) {
        if (a()) {
            String str = d.a(paramString);
            File file1 = new File(a, str);
            File file2 = file1.getParentFile();
            if (!file2.exists())
                file2.mkdirs();
            try {
                if (paramBitmap != null)
                    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file1));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    public Bitmap a(String paramString) {
        if (a()) {
            String str = d.a(paramString);
            File file = new File(a, str);
            if (file.exists())
                try {
                    return BitmapFactory.decodeStream(new FileInputStream(file));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
        }
        return null;
    }

    private boolean a() {
        String str = Environment.getExternalStorageState();
        if ("mounted".equals(str))
            return true;
        return false;
    }
}