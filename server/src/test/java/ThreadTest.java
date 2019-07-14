/**
 *    Copyright (c) 2018-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/28
 */
public class ThreadTest {
    public static volatile Test test = new Test();

    public static void main(String[] args) throws Exception {
        System.out.println("main=" + System.identityHashCode(test));
        for (int i = 0; i < 10; i++) {
            if (i == 1 || i ==6) {
                Thread thread = new Thread(() -> {
                    test = new Test();
                    System.out.println("parent=" + System.identityHashCode(test));
                });
                thread.start();
            } else {
                Thread thread = new Thread(() -> {
                    Test test1 = test;
                    if (RandomUtils.nextBoolean()) {
                        try {
                            Thread.sleep(1000 * 2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("sub=" + System.identityHashCode(test1));
                });
                thread.start();
            }
        }

        Thread.sleep(1000 * 60 * 60);
    }

    public static class Test {

    }
}
