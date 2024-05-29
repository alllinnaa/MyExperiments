package test1;

import java.util.Calendar;
import java.util.Date;

public class Emploee extends Person{


    public Emploee(String name, String surname, Calendar birthDate) {
        super(name, surname, birthDate);
    }

    @Override
    public void addAge(int years) {
        age+=years;
    }
}
