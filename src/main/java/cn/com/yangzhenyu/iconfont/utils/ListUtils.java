package cn.com.yangzhenyu.iconfont.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/7/15
 * Time: 16:39
 */
public class ListUtils {
    public static List<List<Integer>> splitList(List<Integer> list, int size) {
        List<List<Integer>> result = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < list.size(); i = i + size) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                temp.add(list.get(index++));
                if (index >= list.size())
                    break;
            }
            result.add(temp);
        }
        return result;
    }
}
