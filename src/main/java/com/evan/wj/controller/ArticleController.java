package com.evan.wj.controller;

import com.evan.wj.pojo.JotterArticle;
import com.evan.wj.result.Result;
import com.evan.wj.service.JotterArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ArticleController {

    @Autowired
    JotterArticleService jotterArticleService;

    @RequestMapping(value = "/api/saveArticle", method = RequestMethod.POST)
    public Result saveArticle(@RequestBody JotterArticle article) {
        System.out.print(article.getContentHtml());
        JotterArticle jotterArticle =  jotterArticleService.add(article);
        return Result.success();
    }
}
