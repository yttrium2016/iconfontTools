package cn.com.yangzhenyu.iconfont.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/13
 * Time: 13:38
 */
public class FileUtils {


    /**
     * 删除文件或者文件夹以下所有文件
     *
     * @param file
     */
    public static void deleteAllFilesOfDir(File file) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            boolean delete = file.delete();
            if (!delete) {
                System.gc();    //回收资源
                file.delete();
            }
            return;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        file.delete();
    }

    /**
     * 获取所有文件的名字
     *
     * @param file
     * @return
     */
    public static List<String> getFileNames(File file) {
        List<String> result = new ArrayList<>();
        if (!file.exists())
            return null;
        if (file.isFile()) {
            result.add(file.getName());
            return result;
        }
        File[] files = file.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile())
                    result.add(files[i].getName());
            }
            return result;
        }
        return null;
    }


    /**
     * 删除单个文件
     *
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile())
            return file.delete();
        else
            return false;
    }

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String File2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 拷贝文件夹
     *
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        //文件名称列表
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

            if (new File(oldPath + file.separator + filePath[i]).isFile()) {
                copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }


    /**
     * 文件复制
     * @param srcPath 源文件路径
     * @param newFileName 复制后存放路径
     * @throws Exception
     */
    public static void copyFile(String srcPath, String newFileName)  {
        File srcFile = new File(srcPath);

        File targetFile = new File(newFileName);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(targetFile);
            //从in中批量读取字节，放入到buf这个字节数组中，
            // 从第0个位置开始放，最多放buf.length个 返回的是读到的字节的个数
            byte[] buf = new byte[8 * 1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch(Exception e){
                System.out.println("关闭输入流错误！");
            }
            try{
                if(out != null){
                    out.close();
                }
            }catch(Exception e){
                System.out.println("关闭输出流错误！");
            }
        }

    }


}
