package com.granium.domain.mongo.repository

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.repository.extension.ArticleRepositoryExtended
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by Sasha.Chepurnoi on 07.12.16.
 */
interface ArticleRepository : MongoRepository<Article, String>, ArticleRepositoryExtended{

  fun findArticleByAuthor(author: String): MutableList<Article>
  fun findArticleByAuthorInOrderByPostedDesc(authors: Iterable<String>, pageable: Pageable): Page<Article>
  fun findArticleByTitleLikeOrShortLikeOrderByPostedDesc(title: String, short: String, pageable: Pageable): Page<Article>
  fun findArticleByTagsContains(tag: String, pageable: Pageable): Page<Article>
}
