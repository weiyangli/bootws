package com.lwy.bootws.service;

import com.lwy.bootws.bean.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;



@Component
public interface NewsEsService extends ElasticsearchRepository<News, Long> {

}
