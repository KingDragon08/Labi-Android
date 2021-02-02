package cn.wildfire.chat.app.redpacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class g {
    public static boolean a(File paramFile, String paramString1, String paramString2) {
        File file = new File(paramFile, paramString1);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            outputStreamWriter.write(paramString2);
            outputStreamWriter.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return false;
        } catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
        return true;
    }

    public static String a() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("KLog_");
        stringBuilder.append(Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4));
        stringBuilder.append(".txt");
        return stringBuilder.toString();
    }
}

