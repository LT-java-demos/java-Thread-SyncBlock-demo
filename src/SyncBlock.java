import java.util.ArrayList;
import java.util.List;

public class SyncBlock {
    public static void test(List<String> list) {
        System.out.println(Thread.currentThread().getName() + "进入方法");
        synchronized (list) {
            for (String s : list) {
                System.out.println(s + "\tprint by " + Thread.currentThread().getName());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final List<String> list1 = new ArrayList<String>();
        list1.add("Tom");
        list1.add("jerry");
        list1.add("alice");
        list1.add("john");
        list1.add("Leo");
        final List<String> list2 = new ArrayList<String>();
        list2.addAll(list1);
        new Thread() {
            @Override
            public void run() {
                test(list1);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                test(list1);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                test(list2);
            }
        }.start();
    }
}
