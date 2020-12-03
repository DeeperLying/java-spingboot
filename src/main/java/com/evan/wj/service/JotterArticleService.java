package com.evan.wj.service;

import com.evan.wj.dao.JotterArticleDAO;
import com.evan.wj.pojo.JotterArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JotterArticleService {
    @Autowired
    private JotterArticleDAO jotterArticleDAO;

    public JotterArticle add(JotterArticle jotterArticle) {
        return jotterArticleDAO.save(jotterArticle);
    }
}
