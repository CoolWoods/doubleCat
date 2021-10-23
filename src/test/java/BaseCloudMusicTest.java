import com.alibaba.fastjson.JSONObject;
import com.doublecat.mapper.DcGroupMapper;
import com.doublecat.service.impl.QueryServiceImpl;
import com.doublecat.utils.HttpEntityUtil;
import com.doublecat.utils.HttpsClientRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/3 0:16
 * @Modified By:
 */
public class BaseCloudMusicTest {
    @Autowired
    private DcGroupMapper dcGroupMapper;
    private QueryServiceImpl queryService = new QueryServiceImpl();
    RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
//    @Value("${jx3api.url}")
//    private String jx3apiUrl;

    public void testQuery() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "冰心诀");
        String s = JSONObject.toJSONString(map);
        HttpEntity<Object> entity = HttpEntityUtil.defaultHttpEntity(s);
        ResponseEntity<String> forObject = restTemplate.postForEntity("https://jx3api.com/app/macro", entity, String.class);
        System.out.println(forObject);
    }

    public static void main(String[] args) {
        BaseCloudMusicTest baseCloudMusicTest = new BaseCloudMusicTest();
        baseCloudMusicTest.testQuery();
    }
}
