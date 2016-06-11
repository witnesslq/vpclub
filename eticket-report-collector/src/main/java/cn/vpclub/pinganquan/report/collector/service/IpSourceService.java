package cn.vpclub.pinganquan.report.collector.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/10.
 * ip来源地服务
 * 即：ip与地名的映射服务
 */
@Service
public class IpSourceService {

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 调用淘宝的ip地址服务
     * 1秒支持调用10次
     * @param ip
     * @return
     */
    public String getSourceFromTaobao(String ip) throws IOException {
        try {
            String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
            RestTemplate template = new RestTemplate();
            String result = template.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(result);
            if (rootNode.get("code").asText() == "1") {
                return "error"; // ip地址有误
            } else {
                JsonNode dataNode = rootNode.get("data");
                String region = dataNode.get("region").asText();
                String city = dataNode.get("city").asText();
                String county = dataNode.get("county").asText();
                return region + city + county;
            }
        } catch (Exception exp) {
            getSourceFromSina(ip);
            return "error";
        }
    }


    /**
     * 调用新浪的ip地址服务
     * 1秒支持调用700~800
     * @param ip
     * @return
     */
    public String getSourceFromSina(String ip) {
        try {
            String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip;
            RestTemplate template = new RestTemplate();
            String result = template.getForObject(url, String.class);

            JsonNode rootNode = objectMapper.readTree(result);
            String province = rootNode.get("province").asText();
            String city = rootNode.get("city").asText();
            String district = rootNode.get("district").asText();
            return province + city + district;
        } catch (Exception exp) {
            return "error";
        }
    }

}
