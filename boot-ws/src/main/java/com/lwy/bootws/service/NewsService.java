package com.lwy.bootws.service;

import com.lwy.bootws.bean.News;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    void saveNews(String title, String content, Long id);

    List<News> findNews(String title, String content, Pageable pageable);

    List<News> findNews(String title, Pageable pageable);
}
