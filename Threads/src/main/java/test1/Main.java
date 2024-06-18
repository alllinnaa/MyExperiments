package test1;

public class Main {

    public static void main(String[] args) {

       Thread thread1=new Thread(()->{
           for (int i=0;i<100;i++){
               System.out.println("i="+i);
           }
       });
        thread1.run();

        Thread thread2=new Thread(()->{
            for (int p=0;p<100;p++){
                System.out.println("p="+p);
            }
        });
        thread2.start();


        for (int k=0;k<100;k++){
            System.out.println("k="+k);
        }

    }

    public class MyThread extends Thread{


        @Override
        public void run() {
            super.run();
        }
    }
}

