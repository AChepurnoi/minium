package com.granium.service

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Comment
import com.granium.domain.mongo.entity.Draft
import com.granium.domain.mongo.repository.ArticleRepository
import com.granium.domain.psql.entity.User
import com.granium.service.utility.convert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
open class ArticleServiceImpl : ArticleService {

  @Autowired lateinit var articleRepo: ArticleRepository

  @CachePut(cacheNames = arrayOf("articles"), key = "#result.id")
  private fun save(article: Article): Article {
    return articleRepo.save(article)
  }


  @Caching(evict = arrayOf(
      CacheEvict(cacheNames = arrayOf("articlesAll", "feed"), allEntries = true),
      CacheEvict(cacheNames = arrayOf("articles"), key = "#articleId")))
  override fun delete(caller: User, articleId: String) {
    val article = findById(articleId) ?: throw RuntimeException()
    if (article.author != caller.username) throw RuntimeException()
    articleRepo.delete(article)
  }

  @Cacheable(cacheNames = arrayOf("articles"), key = "#id")
  override fun findById(id: String): Article? = articleRepo.findOne(id)


  @Cacheable(cacheNames = arrayOf("articlesAll"))
  override fun findAll(caller: User): List<Article> = articleRepo.findArticleByAuthor(caller.username)


  @Caching(evict = arrayOf(
      CacheEvict(cacheNames = arrayOf("articlesAll", "feed"), allEntries = true)))
  override fun publish(draft: Draft, user: User) {
    val article = convert(draft)
    save(article)
  }

  @Caching(evict = arrayOf(
      CacheEvict(cacheNames = arrayOf("articlesAll", "feed"), allEntries = true),
      CacheEvict(cacheNames = arrayOf("articles"), key = "#article.id")))
  override fun comment(article: Article, comment: Comment) {
    article.comments.add(comment)
    save(article)
  }

  @Caching(evict = arrayOf(
      CacheEvict(cacheNames = arrayOf("articlesAll", "feed"), allEntries = true),
      CacheEvict(cacheNames = arrayOf("articles"), key = "#article.id")))
  override fun removeComment(article: Article, comment: Comment, user: User) {
    if (comment.author != user.login) throw RuntimeException() // no perm
    article.comments.remove(comment)
    save(article)
  }

  @Caching(evict = arrayOf(
      CacheEvict(cacheNames = arrayOf("articlesAll", "feed", "topArticles"), allEntries = true),
      CacheEvict(cacheNames = arrayOf("articles"), key = "#article.id")))
  override fun like(article: Article, user: User) {
    if (article.liked.contains(user.username)) {
      article.liked.remove(user.username)
    } else {
      article.liked.add(user.username)
    }
//    @TODO find out why prepersist isn't calling
    article.prepersist()
    save(article)
  }

  @Cacheable(cacheNames = arrayOf("feed"))
  override fun loadFeed(caller: User, page: Int): Page<Article> {
    val pageRequest = PageRequest(page, 20)
    Thread.sleep(2000)
    val records = articleRepo.findArticleByAuthorInOrderByPostedDesc(caller.following, pageRequest)
    return records
  }

  override fun searchContent(query: String, page: Int): Page<Article> {
    val records = articleRepo.findArticleByTitleLikeOrShortLikeOrderByPostedDesc(query, query, PageRequest(page, 20))
    return records
  }


  override fun searchTags(query: String, page: Int): Page<Article> {
    val tags = query.split(" ")
        .filter { it.first() == '#' }
        .fold(StringBuffer()) { b, arg -> b.append("${arg.drop(1)} ") }
        .trimEnd()
        .toString()
    val records = articleRepo.findArticleByTagsContains(tags, PageRequest(page, 20))
    return records
  }
}