package com.example.mvvmdemo.android;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class ScriptForJava {
    private static int index = 1;

    public static class ReflectionTest {
        private int field = 0;

        private int getData(int a) {
            return a;
        }
    }

    private static ReferenceQueue<byte[]> rq = new ReferenceQueue<byte[]>();
    private static int _1M = 1024 * 1024;



    public void test(int a) throws RuntimeException {
        if (a == 0) {
            throw new RuntimeException("error");
        }
    }

    public static void call() {
        index++;
        call();
    }

    // 两个栈实现队列
    public static class Solution {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        public void push(int node) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            stack1.push(node);
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }

        public int pop() {
            return stack2.pop();
        }

        public <T> T find(int key, T t) {
            if (key > 10) {
                return t;
            } else return null;
        }
    }

    // 寻找第K大的数
    public static class Solution1<T> {
        T a;

        public T find(int key, T t) {
            if (key > 10) {
                return t;
            } else return null;
        }

        public void showKeyValue1(List<?> obj) {
        }
    }

    public static void quickSort(int[] list, int start, int end) {
        if (start >= end) {
            return;
        }
        int temp = list[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && list[j] >= temp) {
                j--;
            }
            while (i < j && list[i] <= temp) {
                i++;
            }
            if (i < j) {
                int t1 = list[i];
                list[i] = list[j];
                list[j] = t1;
            }
        }
        list[start] = list[i];
        list[i] = temp;
        quickSort(list, start, i - 1);
        quickSort(list, i + 1, end);
    }

    //        int[] a = {0,8,2,7,5,11,1,3,9,10,30};
    public static void maopao(int[] a) {
        for (int i = 0; i < a.length; i++) {

            for (int j = a.length - 1; j > i; j--) {
                if (a[j] > a[j - 1]) {
                    int t = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = t;
                }
            }

        }
    }

    // 二叉树遍历
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }

        /**
         * ======0
         * ===1   2
         * =3   5
         * 4       6
         */
        public static TreeNode t = new TreeNode(0);
        public static TreeNode t1 = new TreeNode(1);
        public static TreeNode t2 = new TreeNode(2);
        public static TreeNode t3 = new TreeNode(3);
        public static TreeNode t4 = new TreeNode(4);
        public static TreeNode t5 = new TreeNode(5);
        public static TreeNode t6 = new TreeNode(6);

        static {
            t.left = t1;
            t.right = t2;
            t1.left = t3;
            t3.left = t4;
            t2.left = t5;
            t5.right = t6;
        }
    }


    public static void xianxu(TreeNode treeNode) {
        if (treeNode != null)
            System.out.println(treeNode.val);
        if (treeNode.left != null)
            xianxu(treeNode.left);
        if (treeNode.right != null)
            xianxu(treeNode.right);
    }

    // 队列算法 翻转链表

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void fanzhuan(ListNode listNode){
        if(listNode == null){
            return;
        }
        ListNode after = listNode.next;
        listNode.next = null;
        while(after!=null) {
            ListNode temp = after.next;
            after.next = listNode;
            listNode = after;
            after = temp;
        }
    }

    // 死锁
    public void doSync(){
        // 死锁
        String a = "1";
        String b = "2";
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a){
                    try {
                        System.out.println("call a start");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (b){
                        System.out.println("call b start");
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (b){
                    try {
                        System.out.println("call b1 start");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (a){
                        System.out.println("call a1 start");
                    }
                }

            }
        }).start();
    }

    // 单利
    public static class SingleInstance{
        private volatile static SingleInstance instance;
        public static SingleInstance getInstance(){
                if(instance == null){
                    synchronized (SingleInstance.class){
                        if(instance == null) {
                            instance = new SingleInstance();
                        }
                    }
                }
            return instance;
        }
    }

    public static class SingleInstance1{
        private static SingleInstance1 singleInstance1;
        private static class Instance{
            public static SingleInstance1 instance1 = new SingleInstance1();
        }
        public static SingleInstance1 getInstance(){
            return Instance.instance1;
        }
    }

    public static class A {
        private int a;
        private int getA(){
            System.out.println(a);
            return a+10;
        }
        A(int a){this.a = a;}
    }
    public static void fanshe(){
        try {
            A a = new A(2);
            Class A = Class.forName("com.example.mvvmdemo.android.ScriptForJava$A");
            Method[] declaredMethods = A.getDeclaredMethods();
            Field field = A.getDeclaredField("a");
            Field[] declaredFields = A.getDeclaredFields();
            field.setAccessible(true);
            System.out.println(field.get(a));

            Method get = A.getDeclaredMethod("getA");
            get.setAccessible(true);
            int a1 = (int)get.invoke(a);
            System.out.println(a1);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public abstract static class Ads{
        public abstract class LoadState{
             void onLoaded(){
                 System.out.println("abstract onLoaded");
             }
             void onError(){
                 System.out.println("abstract onError");
             }
        }
        public abstract void loadAd(LoadState loadState);
        public abstract void showAd();
        public void loadAndShowAd(){
            loadAd(new LoadState() {
                @Override
                public void onLoaded() {
                    super.onLoaded();
                    reportLoadOk();
                    showAd();
                }

                @Override
                public void onError() {
                    reportLoadError();
                }
            });
        }

        public void reportLoadOk(){
            System.out.println("reportLoadOk");
        }
        public void reportLoadError(){
            System.out.println("reportLoadError");
        }

        public void reportShowOk(){}

    }

    public static class Ads1 extends Ads{

        @Override
        public void loadAd(LoadState loadState) {
            System.out.println("load ads1");
            try {
                Thread.sleep(100);
                loadState.onLoaded();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void showAd() {
            System.out.println("load show1");
        }
    }


    public ListNode[] reverseGroup(ListNode head,int k){
        ListNode left = null;
        ListNode right = head;
        ListNode temp1 = head;
        for(int i= 0;i<k;i++){
            if(temp1 == null){
                return new ListNode[]{head,null};
            }
            temp1 = temp1.next;
        }
        for(int i= 0;i<k;i++){
            ListNode temp = right.next;
            right.next = left;
            left = right;
            right = temp;
        }
        return new ListNode[]{left,right};
    }
    public ListNode reverseKGroup (ListNode head, int k) {
        // write code here
        ListNode[] nodes = reverseGroup(head,k);
        ListNode left = nodes[0];
        ListNode result = left;
        ListNode now = nodes[1];

        while(now != null ){
            ListNode right = left;
            while(right!=null){
                right = right.next;
            }
             nodes = reverseGroup(now,k);
             left = nodes[0];
             now = nodes[1];
            right.next = left;
        }
        return result;

    }
    // 状态转移方程式
    public static int FindGreatestSumOfSubArray(int[] array) {
        int max = array[0];
        int lastSum = 0;
        for(int i=0;i<array.length;i++){
            lastSum = Math.max(lastSum+array[i],array[i]);
            max = Math.max(lastSum,max);
        }
        return max;
    }
    public static int maxLength (int[] a) {
        // write code here
        int i =0;
        int j =0;
        int max = 0;
        int sum = 0;

        for(;j< a.length;j++){
            for(int k=i;k<j;k++){
                if(a[k] ==a[j]){
                    i = k+1;
                    sum = j -i;

                    break;
                }
            }
            sum =sum+1;
            if(sum>max){
                max = sum;
            }
            System.out.println(sum);


        }
        return max;

    }

    public static String solve (String s, String t) {
        int len1=s.length();
        int len2=t.length();
        // 首先要保证两个数的位数要保持一致
        while(len1<len2){
            s="0"+s;
            len1++;
        }
        while(len1>len2){
            t="0"+t;
            len2++;
        }
        System.out.println(s+ "__"+ t);
        byte[] s1 = s.getBytes();
        byte[] t1 = t.getBytes();
        StringBuilder ans = new StringBuilder();
        int carry=0;  // 定义一个变量作为进位位
        for(int i=len1-1;i>=0;i--){
            int tmp=(s1[i]-'0'+t1[i]-'0'+carry);
            System.out.println(s1[i]-'0');
            ans = ans.append((char)((tmp%10)+ '0')) ;
            carry=tmp/10;  // 不断更新进位位

        }
        // 最后还需要特判进位位
        if(carry == 1){
            ans =ans.append((char)(1+'0'));
        }
        String s2 =  ans.reverse().toString();
        return s2;
    }

    public String LCS (String str1, String str2) {
        // write code here
        int maxlength = 0;
        int maxi = 0;
        int[][] dp = new int[str1.length()][str2.length()];
        for(int i=0;i<str1.length()-1;i++){
            for(int j=0;j<str2.length()-1;j++){
                if(str1.charAt(i+1) == str1.charAt(j+1)){
                    dp[i+1][j+1] = dp[i][j] +1;
                    if(dp[i+1][j+1] > maxlength){
                        maxlength = dp[i+1][j+1];
                        maxi = i;
                    }
                }
                else{
                    dp[i][j] = 0;
                }
            }
        }
        return str1.substring(maxi-maxlength+1,maxi);

    }

    public static long maxWater (int[] a) {
        // write code here [3,1,2,5,2,4]
        int i = 0;
        int j = a.length -1;
        int sum = 0;
        int maxl = 0;
        int maxr = 0;
        while(i<j){
            maxl = Math.max(maxl,a[i]);
            maxr = Math.max(maxr,a[j]);
            if(maxl<maxr){
                sum+= maxl - a[i++];
            }else{
                sum+= maxr - a[j--];
            }
        }
        return sum;
    }

    public static int maxProfit (int[] prices) {
        // write code here
        int min = prices[0];
        int max = 0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<min){
                min = prices[i];
            }
            if(prices[i]-min > max){
                max = prices[i]-min;
            }

        }
        return max;
    }

    public int MoreThanHalfNum_Solution(int [] numbers) {
        int cond = -1;
        int cnt = 0;
        for (int i=0; i<numbers.length; ++i) {
            if (cnt == 0) {
                cond = numbers[i];
                ++cnt;
            }
            else {
                if (cond == numbers[i]) ++cnt;
                else --cnt;
            }

        }
        cnt = 0;
        for (int k :numbers) {
            if (cond == k) ++cnt;
        }
        if (cnt > numbers.length / 2) return cond;
        return 0;
    }

    public static int sum1 =0;
    public static boolean hasPathSum(TreeNode treeNode,int sum){
        if(treeNode == null){
            return false;
        }
        sum1+=treeNode.val;
        System.out.println(treeNode.val+" ++  "+sum1);
        if(treeNode.left!=null){
         boolean l =  hasPathSum(treeNode.left,sum);
         if(l == true){
             return true;
         }

        }
        if(treeNode.right !=null){
           boolean r = hasPathSum(treeNode.right,sum);
           if(r == true){
               return true;
           }
        }
        if(treeNode.left ==null && treeNode.right == null){
            if(sum == sum1){
                return true;
            }
        }
        System.out.println(treeNode.val+"__ "+sum1);
        sum1-=treeNode.val;
        return false;
    }

    public static void main(String[] args) {
//        fanshe();
//        Ads ads = new Ads1();
//        ads.loadAndShowAd();
        TreeNode node5= new TreeNode(5);
        TreeNode node4 = new TreeNode(4);
        TreeNode node8 = new TreeNode(8);
        TreeNode node1 = new TreeNode(1);
        TreeNode node11 = new TreeNode(11);
        TreeNode node9 = new TreeNode(9);
        TreeNode node2 = new TreeNode(2);
        TreeNode node7 = new TreeNode(7);
        node5.left = node4;
        node5.right = node8;
        node4.left = node1;
        node4.right = node11;
        node8.right = node9;
        node11.left = node2;
        node11.right = node7;


        System.out.println(hasPathSum(node5,22));
    }
}
