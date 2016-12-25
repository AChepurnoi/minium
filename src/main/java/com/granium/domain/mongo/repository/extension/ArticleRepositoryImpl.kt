package com.granium.domain.mongo.repository.extension

import com.granium.domain.mongo.entity.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Query

/**
 * Created by Sasha.Chepurnoi on 17.12.16.
 */
open class ArticleRepositoryImpl : ArticleRepositoryExtended {

  @Autowired lateinit var mongoTemplate: MongoTemplate

  override fun findTopArticles(): List<Article> {
//    @TODO Find out why this is not working as supposed
    val query = BasicQuery("{}").with(Sort(Sort.Order(Sort.Direction.DESC, "likes"))).limit(5)
    return mongoTemplate.find(query,Article::class.java)
  }
}