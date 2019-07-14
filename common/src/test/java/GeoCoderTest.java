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
import com.alibaba.fastjson.JSON;
import com.github.fartherp.demo.common.enums.City;
import com.github.fartherp.framework.common.util.HttpClientUtil;
import com.github.fartherp.framework.common.util.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/26
 */
public class GeoCoderTest {

    public static final String URL = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s";

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\project\\juzix\\xinyue\\56hui-web-server\\common\\src\\main\\resources\\com\\github\\fartherp\\demo\\common\\citys.json");
        String html = FileUtils.readFileToString(file);
        Map<String, List<String>> map = new HashMap<>();
        List<City> cityList = JSON.parseArray(html, City.class);
        File file1 = new File("D:\\newcitys.json");
        cityList.forEach(c -> {
            List<String> list = new ArrayList<>(2);
            list.add(c.getName());
            list.add(c.getParent());
            map.put(c.getValue(), list);
        });
        cityList.forEach(c -> {
            if (c.getParent() != null) {
                try {
                    String a = HttpClientUtil.executeGet(String.format(URL, map.get(c.getParent()).get(0) + c.getName(), ""));
                    GeoCoder coder = JSON.parseObject(a, GeoCoder.class);
                    if (coder != null && coder.getResult() != null && coder.getResult().getLocation() != null) {
                        c.setLongitude(coder.getResult().getLocation().getLng());
                        c.setLatitude(coder.getResult().getLocation().getLat());
                    }
                } catch (Exception e) {

                }
            }
        });
        FileUtils.write(file1, JsonUtil.toJson(cityList));
    }

    public static class GeoCoder {
        private Integer status;
        private GeoCoderResult result;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public GeoCoderResult getResult() {
            return result;
        }

        public void setResult(GeoCoderResult result) {
            this.result = result;
        }
    }

    public static class GeoCoderResult {
        private Location location;
        private Integer precise;
        private Integer confidence;
        private Integer comprehension;
        private String level;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Integer getPrecise() {
            return precise;
        }

        public void setPrecise(Integer precise) {
            this.precise = precise;
        }

        public Integer getConfidence() {
            return confidence;
        }

        public void setConfidence(Integer confidence) {
            this.confidence = confidence;
        }

        public Integer getComprehension() {
            return comprehension;
        }

        public void setComprehension(Integer comprehension) {
            this.comprehension = comprehension;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    private static class Location {
        private double lng;
        private double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
