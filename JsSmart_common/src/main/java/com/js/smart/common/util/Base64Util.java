package com.js.smart.common.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Js on 2016/5/20.
 */
public class Base64Util {

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     */
    public static String bitmap(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    public static String bitmap(Drawable drawable) {
        Bitmap bitmap;
//        if (drawable instanceof GlideBitmapDrawable || drawable instanceof SquaringDrawable)
//            bitmap = ((GlideBitmapDrawable)drawable.getCurrent()).getBitmap();
//        else
            bitmap = ((BitmapDrawable)drawable).getBitmap();
        return bitmap(bitmap);
    }

    /**
     * 文件转String
     */
    public static String encodeFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    public static String encodeFile(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    public static String encodeFile(InputStream is) throws Exception {
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * String转文件
     */
    public static void decoderFile(String base64Code, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }


}
