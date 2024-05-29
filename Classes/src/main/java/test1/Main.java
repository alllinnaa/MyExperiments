package test1;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//
//        String name;
//        while (true) {
//            System.out.print("Input name: ");
//            name = in.nextLine().trim();
//            if (!name.isEmpty()) {
//                break;
//            } else {
//                System.out.println("Name cannot be empty. Please enter a valid name.");
//            }
//        }
//
//        int age;
//        while (true) {
//            System.out.print("Input age: ");
//            if (in.hasNextInt()) {
//                age = in.nextInt();
//                if (age > 0) {
//                    break;
//                } else {
//                    System.out.println("Age must be a positive integer. Please enter a valid age.");
//                }
//            } else {
//                System.out.println("Invalid input. Please enter a number for age.");
//                in.next();
//            }
//        }
//
//        Dog dog = new Dalmention(name, age);
//        System.out.println(dog);
//        in.close();
//        System.out.println(dog.getIndex());
        Calendar date = null;
        try {
            date = new GregorianCalendar(2004,Calendar.SEPTEMBER, 22);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Person p=new Emploee("Alina","Sokolova", date) ;
        System.out.println(p);

        p.addAge(3);

        System.out.println(p);



    }


}


