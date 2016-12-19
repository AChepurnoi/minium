package com.granium.service

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Comment
import com.granium.domain.mongo.entity.Draft
import com.granium.domain.psql.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * Created by Sasha.Chepurnoi on 07.12.16.
 */

interface UserService : UserDetailsService {
  fun save(user: User): User
  fun isExist(username: String): Boolean
  fun followToggle(target: User, follower: User)
}

interface ArticleService {
  fun delete(caller: User,articleId: String)
  fun findById(id: String): Article?
  fun publish(draft: Draft, user: User)
  fun comment(article: Article, comment: Comment)
  fun like(article: Article, user: User)
  fun removeComment(article: Article, comment: Comment, user: User)
  fun findAll(caller: User): List<Article>
  fun loadFeed(caller: User, page: Int): Page<Article>
  fun searchContent(query:String, page: Int): Page<Article>
  fun searchTags(query:String, page: Int): Page<Article>

}

interface DraftService {
  fun save(draft: Draft): Draft
  fun findAll(caller: User): List<Draft>
  fun create(author: User, draft: Draft): Draft
  fun delete(caller: User, draftId: String)
  fun findById(caller: User, id: String): Draft
}

interface StatisticService{
  fun topUsers(): List<User>
  fun topArticles(): List<Article>
  fun getCounters(): Pair<Long,Long>
}