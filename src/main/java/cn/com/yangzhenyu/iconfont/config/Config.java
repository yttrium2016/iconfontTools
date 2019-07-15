package cn.com.yangzhenyu.iconfont.config;


import cn.com.yangzhenyu.iconfont.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/19
 * Time: 14:01
 */
public class Config {

    private static Properties prop = null;
    private static File file = null;
    private static String mPath = System.getProperty("user.dir") + "/config.properties";

    static {
        if (file == null) {
            file = new File(mPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("文件创建失败...");
                    e.printStackTrace();
                }
            }
        }
    }

    private static Properties getProp() {
        if (prop == null) {
            prop = new Properties();
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            prop.load(in);
        } catch (IOException e) {
            System.out.println("文件读取出错:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        String is = getProp().getProperty(key);
        if (StringUtils.isBlank(is)) {
            return defValue;
        }
        return "true".equals(is);
    }

    public static void putBoolean(String key, boolean value) {
        getProp().setProperty(key, String.valueOf(value));
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存Boolean出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getInt(String key, int defValue) {
        String intStr = getProp().getProperty(key);
        try {
            return Integer.parseInt(intStr);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static void putInt(String key, int value) {
        getProp().setProperty(key, String.valueOf(value));
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存Int出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key, String defValue) {
        String str = getProp().getProperty(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        }
        return defValue;
    }

    public static void putString(String key, String value) {
        getProp().setProperty(key, value);
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存String出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
