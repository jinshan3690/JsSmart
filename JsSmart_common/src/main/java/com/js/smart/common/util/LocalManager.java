package com.js.smart.common.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LocalManager {

    private Context context;
    private static LocalManager manager;
    private static String appName;
    //默认的共享参数名字
    private static String DEFAULT_PARAMETER_NAME = appName;
    private static String DEFAULT_PARAMETER_NAME_TEMP = appName + ".temp";
    private static String DEFAULT_PARAMETER_NAME_COMMON = appName + ".common";
    //sd卡根目录
    private final static String rootPath = Environment.getExternalStorageDirectory().getPath() + File.separator;
    //文件路径
    private static String filePath = appName + "/file";
    //录音文件路径
    private static String recorderPath = appName + "/cache/recorder";
    //缓存路径
    private static String cachePath = appName + "/cache";

    private LocalManager(Context context) {
        this.context = context;
        appName = context.getApplicationInfo().packageName;
    }

    public static LocalManager getInstance(Context context) {
        if (manager == null) {
            synchronized (LocalManager.class) {
                if (manager == null) {
                    manager = new LocalManager(context);
                }
            }
        }
        return manager;
    }

    public static LocalManager getInstance() {
        DEFAULT_PARAMETER_NAME = DEFAULT_PARAMETER_NAME_TEMP;
        return manager;
    }

    public static LocalManager getInstanceCommon() {
        DEFAULT_PARAMETER_NAME = DEFAULT_PARAMETER_NAME_COMMON;
        return manager;
    }

    //操作共享参数

    /**
     * 保存数据 共享参数
     */
    public void setShare(String key, Object obj) {
        setShare(DEFAULT_PARAMETER_NAME, key, obj);
    }

    public LocalManager setShare(String name, String key, Object obj) {
        if (name != null) {
            SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            Editor editor = preferences.edit();
            if (obj instanceof String) {
                editor.putString(key, (String) obj);
            } else if (obj instanceof Integer) {
                editor.putInt(key, (Integer) obj);
            } else if (obj instanceof Long) {
                editor.putLong(key, (Long) obj);
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, (Boolean) obj);
            } else if (obj instanceof Float) {
                editor.putFloat(key, (Float) obj);
            }
            editor.apply();//无返回值  异步操作
        }
        return this;
    }

    /**
     * 获取数据 共享参数
     */
    public <E extends Object> E getShareByJson(Class<E> cls) {
        return new Gson().fromJson(getShare(DEFAULT_PARAMETER_NAME, cls.getName(), String.class), cls);
    }

    public String getShareString(String key) {
        return getShare(DEFAULT_PARAMETER_NAME, key, String.class);
    }

    public int getShareInt(String key) {
        return getShare(DEFAULT_PARAMETER_NAME, key, Integer.class);
    }

    public float getShareFloat(String key) {
        return getShare(DEFAULT_PARAMETER_NAME, key, Float.class);
    }

    public long getShareLong(String key) {
        return getShare(DEFAULT_PARAMETER_NAME, key, Long.class);
    }

    public boolean getShareBoolean(String key) {
        return getShare(DEFAULT_PARAMETER_NAME, key, Boolean.class);
    }

    public String getShareString(String name, String key) {
        return getShare(name, key, String.class);
    }

    public int getShareInt(String name, String key) {
        return getShare(name, key, Integer.class);
    }

    public float getShareFloat(String name, String key) {
        return getShare(name, key, Float.class);
    }

    public long getShareLong(String name, String key) {
        return getShare(name, key, Long.class);
    }

    public boolean getShareBoolean(String name, String key) {
        return getShare(name, key, Boolean.class);
    }

    private <T extends Object> T getShare(String name, String key, Class<T> cla) {
        Object obj = null;
        if (name != null) {
            SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            if (String.class.equals(cla)) {
                obj = preferences.getString(key, null);
            } else if (Integer.class.equals(cla)) {
                obj = preferences.getInt(key, -1);
            } else if (Float.class.equals(cla)) {
                obj = preferences.getFloat(key, -1);
            } else if (Long.class.equals(cla)) {
                obj = preferences.getLong(key, -1);
            } else if (Boolean.class.equals(cla)) {
                obj = preferences.getBoolean(key, false);
            } else {
                return null;
            }
        }
        return (T) obj;
    }

    /**
     * 删除指定数据 共享参数
     */
    public void clearShare(String key) {
        clearShare(DEFAULT_PARAMETER_NAME_TEMP, key);
    }

    public void clearShare(String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 删除所有数据 共享参数
     */
    public void clearShareAll() {
        clearShareAll(DEFAULT_PARAMETER_NAME_TEMP);
    }

    public void clearShareAll(String name) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 将对象保存在共享参数
     * 只能存 Float Boolean Long Integer String
     */

    public void setShareJson(Object obj) {
        setShare(obj.getClass().getName(), new Gson().toJson(obj));
    }

    public void setShare(Object obj) {
        String name = obj.getClass().getName();
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();

        List<Map<String, Object>> list = ReflectUtil.reflectField(obj);
        for (int i = 0; i < list.size(); i++) {
            String key = (String) list.get(i).get("name");
            Object value = list.get(i).get("value");
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            }
        }
        editor.apply();
    }

    /**
     * 将对象从共享参数取出
     */
    public <T extends Object> T getShare(Class<T> cla) throws Exception {
        Object c = Class.forName(cla.getName()).newInstance();

        List<String> list = ReflectUtil.reflectFieldName(c);
        List<Class> types = ReflectUtil.reflectFieldType(c);
        for (int i = 0; i < list.size(); i++) {
            Class type = types.get(i);
            Object value = getShare(cla.getName(), list.get(i), type);
            if (value != null) {
                Field fld = c.getClass().getDeclaredField(list.get(i));
                fld.setAccessible(true);
                fld.set(c, value);
            }
        }
        return (T) c;
    }

    /**
     * 清楚保存在共享参数的对象
     */
    public void clearShareObject(Class cla) {
        SharedPreferences preferences = context.getSharedPreferences(cla.getName(), Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 修改默认的文件名 共享参数
     */
    public static String getDefaultParameterName() {
        return DEFAULT_PARAMETER_NAME;
    }

    public static void setDefaultParameterName(String name) {
        DEFAULT_PARAMETER_NAME = name;
    }

    //操作文件

    /**
     * 对象流 输出文件
     */
    public synchronized static <T extends Object> void outputObject(T obj) {
        outputObject(obj.getClass().getName(), obj);
    }

    public synchronized static <T extends Object> void outputObject(String fileName, T obj) {
        try {
            String dir = fixDir(getFilePath() + "/data");
            File file = new File(dir + fileName);
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象流 读取文件
     */
    public synchronized static <T extends Object> T inputObject(String fileName) {
        Object obj = null;
        try {
            String dir = fixDir(getFilePath() + "/data");
            File file = new File(dir + fileName);
            Log.d("MiKe", file.getPath() + " 存在 " + file.exists());
            if (!file.exists()) {
                return null;
            }
            FileInputStream fis;
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            Log.d("MiKe", "对象路径:" + file.getPath() + " obj=" + obj);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 对象流List 读取文件
     */
    public synchronized static <T extends Object> ArrayList<T> inputList(String fileName) {
        ArrayList<T> obj = new ArrayList<>();
        try {
            String dir = fixDir(getFilePath() + "/data");
            File file = new File(dir + fileName);
            Log.d("File", file.getPath() + " 存在 " + file.exists());
            if (!file.exists()) {
                return obj;
            }
            FileInputStream fis;
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = (ArrayList<T>) ois.readObject();
            Log.d("File", "对象路径:" + file.getPath() + " obj=" + obj);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 判断本地是否存在某个个对象流
     */
    public synchronized static <T extends Object> Boolean existsObjectStream(T obj) {
        String dir = fixDir(getFilePath() + "/data");
        File file = new File(dir + obj.getClass().getName());
        return file.exists();
    }

    private synchronized static String fixDir(String dir) {
        if (dir == null) {
            dir = "";
        }
        if (!dir.endsWith("/")) {
            dir += "/";
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dir;
    }

    public synchronized static <T extends Object> void removeObj(T obj) {
        try {
            String dir = fixDir(getFilePath() + "/data");
            File file = new File(dir + obj.getClass().getName());
            file.delete();
        } catch (Exception e) {
        }
    }

    public synchronized static <T extends Object> void removeObj(String name) {
        try {
            String dir = fixDir(getFilePath() + "/data");
            File file = new File(dir + name);
            file.delete();
        } catch (Exception e) {
        }
    }

    /**
     * 得到root路径
     */
    public static String getRootPath() {
        return rootPath;
    }

    /**
     * 得到保存对象路径
     */
    public static String getFilePath() {
        File file = new File(rootPath + filePath);
        // 检查文件夹是否存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 得到保存录音文件路径
     */
    public static String getRecorderPath() {
        File file = new File(rootPath + recorderPath);
        // 检查文件夹是否存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 得到缓存路径
     */
    public static String getCachePath() {
        File file = new File(rootPath + cachePath);
        // 检查文件夹是否存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 得到新文件名
     */
    public static String getNewFile(String path, String suffix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        File file = new File(path + File.separator + sdf.format(new Date()) + suffix);
        return file.getPath();
    }

    /**
     * 清除本地缓存
     */
    public static void removeAll() {
        removePath(rootPath + cachePath);
        removePath(rootPath + filePath);
        removePath(rootPath + recorderPath);
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public static void removePath(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Log.i("TEST", "缓存已清除:" + item.getName());
                item.delete();
            }
        }
    }

    public SharedPreferences getNewShare(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
