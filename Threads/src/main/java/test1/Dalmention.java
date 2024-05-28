package test1;

public class Dalmention implements Dog{


    private String name;
    private int age;

    public Dalmention(String name, int age) {

        this.name = name;
        this.age = age;

    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
       return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "Dog`s name: "+name+", age: "+age;
    }
}
