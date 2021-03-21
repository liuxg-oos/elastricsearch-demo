package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(properties="application.properties")
@RunWith(SpringRunner.class)
public class ElasticSearctTest {

    @Autowired
    ElasticsearchTemplate restTemplate;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testCreate() {
        restTemplate.createIndex(Item.class);
        restTemplate.putMapping(Item.class);
    }

    @Test
    public void testAdd() {
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void testUpdate() {
        Item item = new Item(1L, "小米手机7888", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

}
