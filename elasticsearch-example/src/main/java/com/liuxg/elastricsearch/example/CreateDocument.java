package com.liuxg.elastricsearch.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

public class CreateDocument {
    public static void main(String[] args) throws IOException {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));
//        client.prepareIndex("java-index","article","1")
//                .setSource(createDocument())
//                .get();
        client.prepareIndex("java-index","article","2")
                .setSource(createDocmentWithObj(), XContentType.JSON).get();
        client.close();
    }




    public static String createDocmentWithObj() throws JsonProcessingException {
        Article article = new Article();
        article.setId(1);
        article.setTitle("李克强总理出席记者会并回答中外记者提问");
        article.setContent("3月11日，国务院总理李克强在北京人民大会堂出席记者会并回答中外记者提问。这是记者在位于梅地亚中心的分会场采访。新华社记者 金立旺 摄");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(article);
    }


    public static XContentBuilder createDocument() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("id",1)
                .field("title","test")
                .field("content","lllll")
                .endObject();
        return builder;
    }

}
