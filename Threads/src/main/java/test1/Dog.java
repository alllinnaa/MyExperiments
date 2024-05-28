package test1;

public interface Dog {
    int index = 3;
    public String getName() ;

    public void setName(String name) ;

    public int getAge() ;

    public void setAge(int age) ;
    public default int  getIndex() {
        return index;
    }




}
