package org.example;

public class prueba {
    public void test1() {
        System.out.println("Llamar a test2");
        test2();
        System.out.println("Terminado test2");
    }
    public void test2() {
        System.out.println("Llamar a test3");
        test3();
    }
    public void test3() {
        System.out.println("Llamar a test2");
        test4();
    }
    public void test4() {
        System.out.println("Llamar a test2");
        test5();
    }
    public void test5() {
        System.out.println("Hola");
    }
}
