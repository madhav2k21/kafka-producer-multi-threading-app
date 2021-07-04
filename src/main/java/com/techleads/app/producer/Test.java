package com.techleads.app.producer;

public class Test {
    public void add(int a, int b) {
        System.out.println("Sum " + (a + b));
    }
    public void product(int a, int b) {
        System.out.println("Product " + (a * b));
    }
    public void calc(int a, int b) {
        add(a, b);
        product(a, b);
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.calc(10, 20);
    }
}
