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
/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2019/1/3
 */
public class TestAutoClose {
    public static void main(String[] args) {
        testAutoClose();
    }

    private static void testAutoClose() {
        AutoCloseable global_obj1 = null;
        AutoCloseable global_obj2 = null;
        try (AutoCloseable obj1 = new AutoClosedImpl("obj1"); AutoCloseable obj2 = new AutoClosedImpl("obj2");) {
            global_obj1 = obj1;
            int i = 1 / 0;
            global_obj2 = obj2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("before finally close");
                if (global_obj1 != null) {
                    global_obj1.close();
                }

                if (global_obj2 != null) {
                    global_obj2.close();
                }
                System.out.println("after finally close");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class AutoClosedImpl implements AutoCloseable {
        private String name;

        public AutoClosedImpl(String name) {
            this.name = name;
        }

        public void close() throws Exception {
            System.out.println(name + " closing");
        }
    }
}
