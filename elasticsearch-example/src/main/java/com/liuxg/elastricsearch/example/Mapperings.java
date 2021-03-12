package com.liuxg.elastricsearch.example;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class Mapperings {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id").field("type","integer").field("store","yes").endObject()
                            .startObject("title").field("type","string").field("store","yes").field("analyzer","ik_smart").endObject()
                            .startObject("content").field("type","string").field("store","yes").field("analyzer","ik_smart").endObject()
                        .endObject()
                    .endObject()
                .endObject();
        PutMappingRequest request = new PutMappingRequest();
        request.indices("java-index").type("article").source(builder);
        client.admin().indices().putMapping(request).get();
        client.close();
    }
}
