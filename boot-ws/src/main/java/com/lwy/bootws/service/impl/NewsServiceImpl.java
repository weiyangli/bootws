package com.lwy.bootws.service.impl;

import com.lwy.bootws.bean.News;
import com.lwy.bootws.service.NewsEsService;
import com.lwy.bootws.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsEsService newsEsService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveNews(String title, String content, Long id) {
        News news = new News();
        news.setId(id).setContent(content).setPublisher("lwy").setPublishDate(new Date().getTime()).setTitle(title);
        newsEsService.save(news);
    }

    @Override
    public List<News> findNews(String title, String content, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // boolQueryBuilder.must(QueryBuilders.termQuery("id", 1));
        if (Optional.ofNullable(title).isPresent()) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(title, "title"));
        }
//        if (Optional.ofNullable(content).isPresent()) {
//            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(content, "content"));
//        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withTypes("news")
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 10))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, News.class);
    }

    @Override
    public List<News> findNews(String title, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery(title, "title"));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withTypes("news")
                .withQuery(boolQueryBuilder)
//                .withPageable(PageRequest.of(1, 10))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, News.class);
    }
}
