package test1;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Person {
    public String name;
    public String surname;
    public Calendar birthDate;
    public int age;


    public Person(String name, String surname, Calendar birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        Calendar date = new GregorianCalendar();
        this.age= date.getWeekYear()-birthDate.getWeekYear();
    }

    public abstract void addAge(int years);


    @Override
    public String toString() {
        return "Name "+ name+"; surname "+ surname+"; date of birth "+birthDate.getTime()+"; age "+ age;
    }
}
