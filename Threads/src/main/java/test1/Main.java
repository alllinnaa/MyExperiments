package test1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String name;
        while (true) {
            System.out.print("Input name: ");
            name = in.nextLine().trim();
            if (!name.isEmpty()) {
                break;
            } else {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        }

        int age;
        while (true) {
            System.out.print("Input age: ");
            if (in.hasNextInt()) {
                age = in.nextInt();
                if (age > 0) {
                    break;
                } else {
                    System.out.println("Age must be a positive integer. Please enter a valid age.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number for age.");
                in.next(); // Clear the invalid input
            }
        }

        Dog dog = new Dog(name, age);
        addAge(dog);
        System.out.println(dog);
        in.close();
    }

    public static void addAge(Dog dog) {
        dog.setAge(dog.getAge() + 1);
    }
}
