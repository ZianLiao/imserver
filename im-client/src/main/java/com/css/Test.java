package com.css;

import com.css.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by aa on 2020-09-27
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
//        test.stream1();
        List<String> fileIds = new ArrayList<>();
        fileIds.add("111");
        fileIds.add("222");
        String str = String.join(",", fileIds);
        List<String> collect = fileIds.stream().filter(a -> !a.equals("111")).collect(Collectors.toList());
        String straa= "112,434,666";
        List<String> list = Arrays.asList(straa);
        System.out.println(DateTimeUtils.formatCurrentTime2());
        List<String> collect1 = Arrays.asList(straa.split(",")).stream().filter(members -> !members.equals("666")).collect(Collectors.toList());
        System.out.println(DateTimeUtils.formatCurrentTime2());
        List<String> members = Arrays.asList(straa.replace("666","").split(","));
        System.out.println(DateTimeUtils.formatCurrentTime2());

    }

    public void stream1() {
        // 存储0-9的列表
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("原列表：");
        System.out.println(list);

        // 筛选列表中的偶数
        list = list.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());
        System.out.println("列表中的偶数：");
        System.out.println(list);
    }

    public void stream2() {
        // 存储0-9的列表
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("原列表：");
        System.out.println(list);

//        List<Long> idList = list.stream().map(1::2).collect(Collectors.toList());
        // 筛选列表中的偶数
        list = list.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());
        System.out.println("列表中的偶数：");
        System.out.println(list);
    }
}
