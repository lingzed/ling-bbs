package com.ling.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyyMMdd_sshhmm");
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }
}
