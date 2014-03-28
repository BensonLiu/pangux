package com.eadmarket.pangu.module.screen.api;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 3/27/14.
 */
public class QueryKnowledgeCategory {

    private final static int API_VERSION = 1;

    public void execute(Context context) {
        Map<String, Object> result = Maps.newHashMap();

        result.put("version", API_VERSION);
        result.put("success", 1);
        result.put("data", list);

        String json = JSON.toJSONString(result);

        context.put("JSON", json);
    }

    /*
    final static Map<Long, String> CATE_TO_CNNAME
            = ImmutableMap.<Long, String>builder()
            .put(1L, "自然")
            .put(2L, "文化")
            .put(3L, "人物")
            .put(4L, "历史")
            .put(5L, "生活")
            .put(6L, "社会")
            .put(7L, "艺术")
            .put(8L, "经济")
            .put(9L, "科学")
            .put(10L, "体育")
            .put(11L, "技术")
            .put(12L, "地理").build();
    */
    @AllArgsConstructor @Data
    final static class CategoryPair {
        int category;

        String name;
    }

    final static List<CategoryPair> list = Lists.newArrayList(
            new CategoryPair(1, "自然"),
            new CategoryPair(2, "文化"),
            new CategoryPair(3, "人物"),
            new CategoryPair(4, "历史"),
            new CategoryPair(5, "生活"),
            new CategoryPair(6, "社会"),
            new CategoryPair(7, "艺术"),
            new CategoryPair(8, "经济"),
            new CategoryPair(9, "科学"),
            new CategoryPair(10, "体育"),
            new CategoryPair(11, "技术"),
            new CategoryPair(12, "地理")
    );

    public static void main(String[] args) {
        Map<String, Object> result = Maps.newHashMap();

        result.put("version", API_VERSION);
        result.put("success", 1);
        result.put("data", list);

        String json = JSON.toJSONString(result);

        System.out.println(json);
    }

}
