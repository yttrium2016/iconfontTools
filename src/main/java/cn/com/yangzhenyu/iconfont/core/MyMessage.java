package cn.com.yangzhenyu.iconfont.core;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/26
 * Time: 12:09
 */
public class MyMessage extends LogRecord {
    public MyMessage(String msg) {
        super(Level.ALL, msg);
    }
}
