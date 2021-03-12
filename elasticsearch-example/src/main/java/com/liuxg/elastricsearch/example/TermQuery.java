package com.liuxg.elastricsearch.example;

import org.apache.lucene.queryparser.xml.QueryBuilderFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class TermQuery {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));

        SearchRequestBuilder requestBuilder = client.prepareSearch("java-index")
                .setTypes("article");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<em>");
        highlightBuilder.field("title");
        highlightBuilder.postTags("</em>");
        requestBuilder.addSort(SortBuilders.fieldSort("id"))
                .highlighter(highlightBuilder)
                .setFrom(1)
                .setSize(8);
//        SearchResponse response = requestBuilder.setQuery(QueryBuilders.termQuery("id", "23")).get();
//        SearchResponse response = requestBuilder.setQuery(QueryBuilders.termQuery("title", "全国")).get();
//        SearchResponse response = requestBuilder.setQuery(QueryBuilders.termQuery("content", "全国人大代表")).get();
        SearchResponse response = requestBuilder.setQuery(QueryBuilders.queryStringQuery( "全国人大代表")).get();
        SearchHits searchHits = response.getHits();
        System.out.println(searchHits.totalHits);
        SearchHit[] hits = searchHits.internalHits();
        for (SearchHit searchHitFields : hits) {
            System.out.println(searchHitFields.getHighlightFields());
            System.out.println(searchHitFields.getSourceAsString());
        }
        client.close();
    }
}
