package com.lwy.bootws.controller;

import com.alibaba.fastjson.JSON;
import com.lwy.bootws.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    @Autowired
    private NewsService  newsService;

    @GetMapping("/api/news/save")
    public String saveNews(@RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Long id) {
        newsService.saveNews(title, content,id);
        return "success";
    }

    @GetMapping("/api/search")
    public String search(@RequestParam String title,
                         @RequestParam String content,
                         @PageableDefault(page = 1, value = 10) Pageable pageable){
        return JSON.toJSONString(newsService.findNews(title, content, pageable));
    }

    @GetMapping("/api/search2")
    public String search2(@RequestParam String title,
                         @PageableDefault(page = 1, value = 10) Pageable pageable){
        return JSON.toJSONString(newsService.findNews(title, pageable));
    }
}
