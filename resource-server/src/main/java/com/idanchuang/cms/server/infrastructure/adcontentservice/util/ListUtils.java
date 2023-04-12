package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wangzh
 * @Description
 * @Date 2020/12/9 16:25
 * @Version 1.0
 */
public class ListUtils {

    /**
     * 拆分集合
     * 用于批处理，在批量操作数据库时候数据太大的时候最好还是先拆分
     *
     * @param <T>
     * @param resList 要拆分的集合
     * @param count   每个集合的元素个数
     * @return 返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {
        if (resList == null || count < 1) {
            return null;
        }
        List<List<T>> ret = new ArrayList<List<T>>();
        int size = resList.size();
        //数据量不足count指定的大小
        if (size <= count) {
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }

    /**
     * 平均分配集合
     * 把A集合平均分配给B集合
     *
     * @param A
     * @param B
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> Map<V, List<T>> avg(List<T> A, List<V> B) {
        if (A == null || A.size() < 0 || null == B || B.size() < 1) {
            return null;
        }
        Map<V, List<T>> map = new HashMap<>();
        int size = A.size();
        //数据量不足 B 轮询分配
        if (size <= B.size()) {
            for (int k = 0; k < A.size(); k++) {
                List<T> list = new ArrayList<>();
                list.add(A.get(k));
                map.put(B.get(k), list);
            }
        } else {
            // 每组分多个个
            int pre = size / B.size();
            List<List<T>> list = split(A, pre);
            for (int i = 0; i < B.size(); i++) {
                map.put(B.get(i), list.get(i));
            }
            if (B.size() < list.size()) {
                List<T> fex = list.get(B.size());
                V v = B.get(B.size() - 1);
                List<T> begin = map.get(v);
                fex.addAll(begin);
                map.put(v, fex);
            }
        }
        return map;
    }

//    public static void main(String[] args) {
////        List<String> resList = Arrays.asList("A", "B", "C", "D", "E");
////        List<List<String>> ret = split(resList, 4);
////        for (int i = 0; i < ret.size(); i++) {
////            System.out.println(ret.get(i));
////        }
//
//        // 第一种
////        List<String> resList = Arrays.asList("A", "B", "C", "D", "E");
////        List<Integer> resList2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
////        Map<Integer, List<String>> map = avg(resList, resList2);
////        for (Integer key : map.keySet()) {
////            System.out.println("key:" + key + " size:" + map.get(key));
////        }
//    }
}
