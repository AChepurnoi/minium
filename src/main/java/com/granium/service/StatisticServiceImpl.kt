package com.granium.service

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.repository.ArticleRepository
import com.granium.domain.psql.entity.User
import com.granium.domain.psql.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
open class StatisticServiceImpl : StatisticService {
  @Autowired lateinit var articleRepo: ArticleRepository
  @Autowired lateinit var userRepo: UserRepository

  override fun topUsers(): List<User> = userRepo.findTopFive()

  @Cacheable(cacheNames = arrayOf("topArticles"))
  override fun topArticles(): List<Article> {
    Thread.sleep(2000)
    val res = articleRepo.findTopArticles()
    return res
  }

  override fun getCounters(): Pair<Long, Long> = userRepo.count() to articleRepo.count()
}