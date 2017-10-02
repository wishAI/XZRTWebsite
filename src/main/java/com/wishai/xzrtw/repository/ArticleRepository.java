package com.wishai.xzrtw.repository;


import com.wishai.xzrtw.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {

    List<Article> findTop4ByCategoryOrderByCreateDateAsc(String category);
    
    List<Article> findTop3ByCategoryOrderByCreateDateAsc(String category);

    List<Article> findTop10ByCategoryOrderByCreateDateAsc(String category);


    Page<Article> findByCategoryOrderByCreateDateAsc(String category, Pageable pageable);
}
