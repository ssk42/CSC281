/**
 * @author knappa
 * @version 1.0
 */
public class ThreadingExample {

    public static void main(String[] args) {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("a");
                }
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++)
                    System.out.print("b");
            }
        });

        threadOne.start();
        threadTwo.start();
    }

}
