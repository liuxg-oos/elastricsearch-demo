package com.liuxg.elastricsearch.example;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateIndex {
    public static void main(String[] args) {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));
            client.admin().indices().prepareCreate("java-index").get();
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (client != null)
                client.close();
        }
    }
}
