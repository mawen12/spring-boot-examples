package com.mawen.elasticsearch.sample.springboot.controller;

import com.mawen.elasticsearch.sample.springboot.model.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/28
 */
@RestController
public class ArticleController {

    @GetMapping("/search/all")
    public List<Article> searchAll() {
        return null;
    }

}
