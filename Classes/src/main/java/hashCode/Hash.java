package hashCode;

import java.util.Objects;

public class Hash {
    public static void main(String[] args) {
      Person p =new Person("Alina",20);
      System.out.println(p.hashCode());

        Person k =new Person("Alina",20);
        System.out.println(k.hashCode());

        System.out.println(p.equals(k));

    }

    public static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

}
