package edu.school21.models;

public class Car {
    private String model;
    private int age;

    public Car() {
        this.model = "Default model name";
        this.age = 0;
    }

    public Car(String model, int age) {
        this.model = model;
        this.age = age;
    }

    public int makeOlder(int value) {
        this.age += value;
        return this.age;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", age=" + age +
                '}';
    }
}
