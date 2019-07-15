package cn.com.yangzhenyu.iconfont.core;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/26
 * Time: 12:05
 */
public abstract class MyHandler extends Handler {
    @Override
    public abstract void publish(LogRecord record);

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
