package com.liuxg.elastricsearch.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BatchCreateDocument {

    public static void main(String[] args) throws UnknownHostException, JsonProcessingException {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 73; i < 93; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle("全国政协委员马进：建议采取措施规范直播行为，公示逃税主播名单"+i);
            article.setContent("全国政协委员、致公党上海市委专职副主委马进指出，“直播经济”火爆的背后，产生了诸如“儿童高额打赏”“经纪公司刷榜诱导消费者跟风打赏”“网红主播偷税漏税”等乱象，暴露了直播经济背后，相关金融、税务监管的盲区。建议采取措施规范直播行为。马进提出，应将“消费者通过直播平台兑换虚拟货币行为”定义为平台向消费者提供技术服务，消费者的打赏行为为消费行为。马进还建议税务部门要加强对直播平台和网络主播纳税情况监管。对有严重偷逃个人所得税的主播，要予以补缴税款和处罚，并在相关媒体上进行公示。"+i);
            client.prepareIndex().setIndex("java-index").setType("article").setId(""+i).setSource(mapper.writeValueAsString(article), XContentType.JSON).get();
        }
        client.close();
    }
}
