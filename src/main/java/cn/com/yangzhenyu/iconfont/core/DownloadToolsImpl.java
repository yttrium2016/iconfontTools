package cn.com.yangzhenyu.iconfont.core;

import cn.com.yangzhenyu.iconfont.config.Config;
import cn.com.yangzhenyu.iconfont.utils.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/7/15
 * Time: 16:34
 */
public class DownloadToolsImpl implements DownloadTools {

    private static String zipPath = System.getProperty("user.dir") + "/zip";
    private static String tempPath = System.getProperty("user.dir") + "/temp";
    private static String iconPath = System.getProperty("user.dir") + "/icon";

    private Handler handler = null;

    @Override
    public void downloadIcons(String cid, Handler handler) throws RuntimeException {
        this.handler = handler;
        if (StringUtils.isBlank(cid)) {
            throw new RuntimeException("查询ID不能为空");
        }

        //清除所有
        File old = new File(zipPath);
        FileUtils.deleteAllFilesOfDir(old);
        if (!old.mkdir()) throw new RuntimeException("没有创建文件的权限");
        old = new File(tempPath);
        FileUtils.deleteAllFilesOfDir(old);
        if (!old.mkdir()) throw new RuntimeException("没有创建文件的权限");
        old = new File(iconPath);
        FileUtils.deleteAllFilesOfDir(old);
        if (!old.mkdir()) throw new RuntimeException("没有创建文件的权限");

        String url = "https://www.iconfont.cn/api/collection/detail.json?id=" + cid;
        Connection.Response response;
        try {
            response = Jsoup.connect(url).ignoreContentType(true).execute();
            Map<String, Object> map = JacksonUtils.json2map(response.body());
            if (map != null && map.get("code") != null && (Integer) map.get("code") == 200) {
                List<Integer> ids = new ArrayList<>();
                Object temp = ((Map) map.get("data")).get("icons");
                if (temp instanceof List) {
                    List list = (List) temp;
                    for (Object o : list) {
                        if (o instanceof Map) {
                            Integer id = (Integer) ((Map) o).get("id");
                            ids.add(id);
                        }
                    }
                }
                handler.publish(new MyMessage("共" + (ids.size()) + "个图标"));
                if (ids.size() > 20) {
                    List<List<Integer>> lists = ListUtils.splitList(ids, 20);
                    int index = 0;
                    handler.publish(new MyMessage("文件大于20个分多次下载"));
                    for (List<Integer> idsTemp : lists) {
                        handler.publish(new MyMessage("正在下载第" + (index + 1) + "次"));
                        download(idsTemp, "zip" + index++);
                    }
                } else {
                    handler.publish(new MyMessage("文件小于20个直接下载"));
                    download(ids, "zip");
                }

                File fileDir = new File(tempPath);
                handler.publish(new MyMessage("全部下载完成"));
                handler.publish(new MyMessage("开始解压复制"));
                for (File file : Objects.requireNonNull(fileDir.listFiles())) {
                    FileUtils.copyDir(file.getPath(), iconPath);
                }
                handler.publish(new MyMessage("解压复制完成"));
                handler.publish(new MyMessage("清理多余数据"));
                old = new File(zipPath);
                FileUtils.deleteAllFilesOfDir(old);
                old = new File(tempPath);
                FileUtils.deleteAllFilesOfDir(old);
                handler.publish(new MyMessage("清理完成"));
                handler.publish(new MyMessage("下载完成目录:" + iconPath));
            }
        } catch (IOException e) {
            throw new RuntimeException("获取出错" + e.getMessage());
        }

    }


    public void download(List<Integer> ids, String name) throws IOException {
        String url = "https://www.iconfont.cn/api/icon/downloadIcon";

        String ctoken = "";
        String cookiesStr = Config.getString("cookies", "");
        String type = Config.getString("type", "png");
        String color = Config.getString("color", "333333");
        String size = Config.getString("size", "200");

        Map<String, String> cookies = new HashMap<>();
        for (String s1 : cookiesStr.split("; ")) {
            String[] split = s1.split("=");
            cookies.put(split[0], split[1]);
            if (split[0].equals("ctoken")) {
                ctoken = split[1];
            }
        }

        String idStr = "";
        for (Integer id : ids) {
            if ("".equals(idStr)) {
                idStr = id + "|-1";
            } else {
                idStr += "," + id + "|-1";
            }
        }

        Map<String, String> data = new HashMap<>();
        data.put("type", type);
        data.put("ids", idStr);
        data.put("color", color);
        data.put("size", size);
        data.put("ctoken", ctoken);

        Connection.Response resultImageResponse = Jsoup.connect(url)
                .data(data)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();
        String info = resultImageResponse.body();
        if (JacksonUtils.isJson(info)) {
            System.out.println(info);
        } else {
            FileOutputStream out = (new FileOutputStream(new java.io.File(zipPath + "/" + name + ".zip")));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();
            ZipUtils.unZip(new File(zipPath + "/" + name + ".zip"), tempPath);
            //解压
        }
    }
}
