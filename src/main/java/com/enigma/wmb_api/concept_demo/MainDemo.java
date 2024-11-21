package com.enigma.wmb_api.concept_demo;

public class MainDemo {
    public static void main(String[] args) {
        SingletonDesignPatternDemo instance1 = SingletonDesignPatternDemo.getInstance();
        SingletonDesignPatternDemo instance2 = SingletonDesignPatternDemo.getInstance();

        SingletonDesignPatternDemo instance3 = new SingletonDesignPatternDemo();
        SingletonDesignPatternDemo instance4 = new SingletonDesignPatternDemo();


        System.out.println("instance1 == instance2 " + (instance1 == instance2));
        System.out.println("instance3 == instance4 " + (instance3 == instance4));

        GenericDemo genericDemo = new GenericDemo("String");
        GenericDemo genericDemo2 = new GenericDemo(10000);
        GenericDemo genericDemo3 = new GenericDemo(true);

        System.out.println("genericDemo" + genericDemo.getValue().getClass().getSimpleName());
        System.out.println("genericDemo2" + genericDemo2.getValue().getClass().getSimpleName());
        System.out.println("genericDemo3" + genericDemo3.getValue().getClass().getSimpleName());
    }
}
