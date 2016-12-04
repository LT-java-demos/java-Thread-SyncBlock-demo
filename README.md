#java-Thread-SyncBlock-demo

如果方法中只有少量语句是非线性安全的，直接给方法上锁对并发性影响太大。

- demo

[https://github.com/LT-java-demos/java-Thread-staticLock-demo](https://github.com/LT-java-demos/java-Thread-staticLock-demo)

##同步块
只对可能发生问题的代码上锁，减少对并发性的影响

###Demo

```java
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
```


###运行结果

```shell
Thread-0进入方法
Tom	print by Thread-0
jerry	print by Thread-0
alice	print by Thread-0
john	print by Thread-0
Leo	print by Thread-0
Thread-1进入方法
Thread-2进入方法
Tom	print by Thread-2
jerry	print by Thread-2
alice	print by Thread-2
john	print by Thread-2
Leo	print by Thread-2
Tom	print by Thread-1
jerry	print by Thread-1
alice	print by Thread-1
john	print by Thread-1
Leo	print by Thread-1
```