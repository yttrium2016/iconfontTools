package cn.com.yangzhenyu.iconfont.core;

import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/7/15
 * Time: 16:34
 */
public interface DownloadTools {

    void downloadIcons(String id, Handler handler) throws RuntimeException;
}
