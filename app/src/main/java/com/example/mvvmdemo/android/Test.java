package com.example.mvvmdemo.android;

import java.util.ArrayList;

public class Test {
// 0, 1, 1, 2, 3, 5, 8
    static ArrayList<Integer> list = new ArrayList();
    static {
        list.add(0,0);
        list.add(1,1);
    }

    static int add(int i) {
        if (i < 0) {
            return 0;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        for(int j= list.size();j<=i;j++){
            int v = list.get(j-1)+list.get(j-2);
            System.out.println("v: "+ v);
            list.add(j,v);
        }
        return list.get(i);
    }
    public static void main(String[] args) {

        System.out.println(add(5));
    }
}
