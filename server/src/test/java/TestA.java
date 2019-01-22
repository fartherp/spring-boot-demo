/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2019/1/21
 */
public class TestA {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<String> l = list.stream().map(i -> i + "").collect(Collectors.toList());
        System.out.println(l.toString());

        LocalDate localDate = LocalDate.parse("2018-12-21", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(localDate.getYear());
    }
}
