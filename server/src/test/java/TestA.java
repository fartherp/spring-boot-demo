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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2019/1/21
 */
public class TestA {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<String> l = list.stream().map(i -> i + "").collect(Collectors.toList());
        System.out.println(l.toString());

        LocalDate localDate = LocalDate.parse("2018-12-21", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(localDate.getYear());
    }
}
