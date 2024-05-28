package test1;

public class Calculation {
    public static int plus (int... values) {
        int sum = 0;
        for ( int v:values) {
            sum+=v;
        }
        return sum;
    }

}
