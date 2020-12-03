package com.evan.wj.dao;

import com.evan.wj.pojo.JotterArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JotterArticleDAO extends JpaRepository<JotterArticle,Integer> {

}