package com.granium.domain.mongo.repository.extension

import com.granium.domain.mongo.entity.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Created by Sasha.Chepurnoi on 17.12.16.
 */

interface ArticleRepositoryExtended{
  fun findTopArticles(): List<Article>
}