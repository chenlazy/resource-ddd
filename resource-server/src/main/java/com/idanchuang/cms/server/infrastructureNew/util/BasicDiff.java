package com.idanchuang.cms.server.infrastructureNew.util;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-03 17:45
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BasicDiff {

    @Data
    public static class DiffResult{

        private Boolean hasDiff = false;
        private String diffMessage;
        private String oldValue;
        private String newValue;
        private String addValue;
        private String reduceValue;

        @Override
        public String toString() {
            return "DiffResult{" +
                    "hasDiff=" + hasDiff +
                    ", diffMessage='" + diffMessage + '\'' +
                    '}';
        }
    }

    private static final String COMPARE_ROOT = "componentJsonData";

    private static final String ROOT = "root";

    private static final String START_TIME = "startTime";

    private static final String END_TIME = "endTime";

    DiffResult diffResult = new DiffResult();

    private String oldComponentStr = "";

    private String newComponentStr = "";

    public  DiffResult compareObject(Object oldObject,Object newObject, String key, int index) {

        if(oldObject == null || newObject == null){
            diffResult.hasDiff = true;
            diffResult.setDiffMessage(key+" 的value中old和new有一个或者两个为null");
            return diffResult;
        }

        if (diffResult.getHasDiff()) {
            return diffResult;
        }

        if (oldObject.getClass() != newObject.getClass()) {
            diffResult.hasDiff = true;
            diffResult.setDiffMessage(key + "的value的old和new的类型不一致");
            return diffResult;
        }

        if (oldObject instanceof JSONObject && newObject instanceof JSONObject) {
            compareJsonObject((JSONObject) oldObject, (JSONObject) newObject, key, index);
            if (diffResult.getHasDiff()) {
                return diffResult;
            }

        } else if (oldObject instanceof JSONArray && newObject instanceof JSONArray) {
            compareJsonArray((JSONArray) oldObject, (JSONArray) newObject, key, index);
            if (diffResult.getHasDiff()) {
                return diffResult;
            }

        } else {
            String oldStr = oldObject.toString();
            String newStr = newObject.toString();
            if (!oldStr.equals(newStr)) {

                if (key.contains(START_TIME) || key.contains(END_TIME)) {
                    LocalDateTime oldStartTime = oldStr.contains("Z") ? DateTimeUtil.formatLocalDateTime(oldStr) :
                            DateTimeUtil.string2LocalDateTime(oldStr, DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);
                    LocalDateTime newStartTime = newStr.contains("Z") ? DateTimeUtil.formatLocalDateTime(newStr) :
                            DateTimeUtil.string2LocalDateTime(newStr, DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);
                    if (null != oldStartTime && oldStartTime.equals(newStartTime)) {
                        return diffResult;
                    }
                }

                diffResult.hasDiff = true;
                diffResult.setDiffMessage(key + "的value中old和new的值不相等。old:" + oldStr + ",new:" + newStr);
                diffResult.setOldValue(oldComponentStr);
                diffResult.setNewValue(newComponentStr);
                return diffResult;
            }
        }
        return diffResult;
    }

    public  DiffResult compareJsonArray(JSONArray oldJarr, JSONArray newJarr, String key, int index) {
        if(diffResult.getHasDiff()){
            return diffResult;
        }
        if(oldJarr == null || newJarr == null){
            diffResult.hasDiff = true;
            diffResult.setDiffMessage(key+" 的value中两个结果存在null");
            return diffResult;
        }
        if(oldJarr.size() != newJarr.size()){
            diffResult.hasDiff = true;
            if (newJarr.size() > oldJarr.size()) {
                List<String> addArray = new ArrayList<>();
                for (int i = oldJarr.size(); i < newJarr.size(); i++) {
                    addArray.add(newJarr.get(i).toString());
                }
                diffResult.setDiffMessage(key + " 的value中新增了key值");
                diffResult.setAddValue(addArray.toString());
            } else {
                List<String> reduceArray = new ArrayList<>();
                for (int i = newJarr.size(); i < oldJarr.size(); i++) {
                    reduceArray.add(oldJarr.get(i).toString());
                }
                diffResult.setReduceValue(reduceArray.toString());
                diffResult.setDiffMessage(key + " 的value中删除了key值");
            }
            return diffResult;
        }

        //jsonarray中元素是个object，排序之后再比较
        if(oldJarr.size() > 0 && !(oldJarr.get(0) instanceof JSONObject) && !(oldJarr.get(0) instanceof JSONArray)){
            String[] arrOld = new String[oldJarr.size()];
            String[] arrNew = new String[oldJarr.size()];
            List<String> tmp = new ArrayList<>();
            for(int i = 0; i < arrOld.length; i++){
                if (null != oldJarr.get(i) && null != newJarr.get(i)) {
                    arrOld[i] = oldJarr.get(i).toString();
                    arrNew[i] = newJarr.get(i).toString();
                    tmp.add(oldJarr.get(i).toString());
                }
            }
            Arrays.sort(arrOld);
            Arrays.sort(arrNew);
            for(int i = 0; i < arrNew.length; i++){
                if(arrOld[i] != null && !arrOld[i].equals(arrNew[i])){
                    diffResult.hasDiff = true;
                    diffResult.setDiffMessage(key + " 的value中第"+tmp.indexOf(arrOld[i])+"个old和new 值不相等");
                    diffResult.setOldValue(oldJarr.toJSONString());
                    diffResult.setNewValue(newJarr.toJSONString());
                    return diffResult;
                }
            }

        }else{
            for(int i = 0; i < oldJarr.size(); i++){
                if(oldJarr.get(i) != null && newJarr.get(i) != null && oldJarr.get(i).getClass()!= newJarr.get(i).getClass()){
                    diffResult.hasDiff = true;
                    diffResult.setDiffMessage(key + " 的value中old和new 的类型不一致");
                    return diffResult;
                }
                if(oldJarr.get(i) instanceof JSONObject){
                    JSONObject jold = (JSONObject)oldJarr.get(i);
                    JSONObject jnew = (JSONObject)newJarr.get(i);
                    if(jold.equals(jnew)){
                        continue;
                    }else{
                        compareJsonObject((JSONObject)oldJarr.get(i),(JSONObject)newJarr.get(i),key,i);
                        if(diffResult.getHasDiff()){
                            return diffResult;
                        }
                    }
                }else if(oldJarr.get(i) instanceof JSONArray) {
                    compareJsonArray((JSONArray) oldJarr.get(i), (JSONArray) newJarr.get(i), key, i);
                    if(diffResult.getHasDiff()){
                        return diffResult;
                    }
                }
            }
        }
        return diffResult;
    }



    public DiffResult compareJsonObject(JSONObject oldJson, JSONObject newJson, String key, int index) {
        if(diffResult.getHasDiff()){
            return diffResult;
        }
        if(oldJson == null || newJson == null){
            diffResult.hasDiff = true;
            diffResult.setDiffMessage(key+" 的value中两个结果存在null");
            return diffResult;
        }

        Set<String> sold = oldJson.keySet();
        Set<String> snew = newJson.keySet();
        if (key.isEmpty()) {
            key = ROOT;
        }
        //keysize是否相等
        if (sold.size() != snew.size()) {
            diffResult.hasDiff = true;
            if (key.equals(ROOT)) {
                if (snew.size() > sold.size()) {
                    Set<Map.Entry<String, Object>> entries = newJson.entrySet();
                    diffResult.setDiffMessage(key + "新增了" + (snew.size() - sold.size()) + "个容器");
                    Map<String, Object> addValue = new HashMap<>();
                    for (Map.Entry<String, Object> entry : entries) {
                        String knew = entry.getKey();
                        if (!sold.contains(knew)) {
                            //就只查询compData数据
                            Map<String, Object> compMap = JsonUtil.toMap(JsonUtil.toJsonString(entry.getValue()), Map.class);
                            Object compData = compMap.get(COMPARE_ROOT);
                            addValue.put(knew, compData);
                        }
                    }
                    diffResult.setAddValue(JsonUtil.toJsonString(addValue));
                } else {
                    diffResult.setDiffMessage(key + "减少了" + (sold.size() - snew.size()) + "个容器");
                    Set<Map.Entry<String, Object>> entries = oldJson.entrySet();
                    Map<String, Object> reduceValue = new HashMap<>();
                    for (Map.Entry<String, Object> entry : entries) {
                        if (!snew.contains(entry.getKey())) {
                            //就只查询compData数据
                            Map<String, Object> compMap = JsonUtil.toMap(JsonUtil.toJsonString(entry.getValue()), Map.class);
                            Object compData = compMap.get(COMPARE_ROOT);
                            reduceValue.put(entry.getKey(), compData);
                        }
                    }
                    diffResult.setReduceValue(JsonUtil.toJsonString(reduceValue));
                }
            } else {
                diffResult.setDiffMessage(key + " 的keySet的数量不一致，old有" + sold.size() + "个key,new有" + snew.size() + "个key。");
                diffResult.setOldValue(oldComponentStr);
                diffResult.setNewValue(newComponentStr);
            }
            return diffResult;
        }

        //key是否相同
        for (String kold : sold) {
            if (!snew.contains(kold)) {
                diffResult.hasDiff = true;
                diffResult.setDiffMessage(key + "的keyset不包含" + kold);
                diffResult.setOldValue(oldComponentStr);
                diffResult.setNewValue(newComponentStr);
                return diffResult;
            }
        }
        //value进行校验
        for(String kold :sold){

            //此处是进行过滤的，如果哪些字段不需要进行diff，就在这里过滤掉，例如接口返回的globalid,每次返回的值都不一样，不需要进行diff
            if(kold.equals("globalId")) {
                continue;
            }

            Object oldObject = oldJson.get(kold);
            Object newObject = newJson.get(kold);
            if (COMPARE_ROOT.equals(kold)) {
                oldComponentStr = oldObject.toString();
                newComponentStr = newObject.toString();
            }
            compareObject(oldObject, newObject, key + "->" + kold, index);
            if (diffResult.getHasDiff()) {
                return diffResult;
            }

        }

        return diffResult;
    }

}