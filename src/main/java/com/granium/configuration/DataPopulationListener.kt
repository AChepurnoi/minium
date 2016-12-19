package com.granium.configuration

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.repository.ArticleRepository
import com.granium.domain.psql.entity.User
import com.granium.domain.psql.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Sasha.Chepurnoi on 17.12.16.
 */

@Component
@Profile("docker")
open class DataPopulationListener : ApplicationListener<ContextRefreshedEvent> {

  @Autowired lateinit var articleRepo: ArticleRepository
  @Autowired lateinit var userRepo: UserRepository

  override fun onApplicationEvent(event: ContextRefreshedEvent?) {
    val firstPack = (0..250).map { createUser(it) }
    val secondPack = (250..500).map { createUser(it) }
    followers(firstPack, secondPack)
    val users = userRepo.save(firstPack + secondPack)
    val articles = users.flatMap { generateHundedArticles(it) }
    articleRepo.save(articles)

  }


  fun generateHundedArticles(user: User): List<Article> {
    val res = (0..100).map {
      val res = Article()
      res.title = "Title ${user.login} $it"
      res.short = "Short description lorem ${user.login} $it blanditiis deleniti doloremque ducimus eius enim fuga harum incidunt ipsum libero non, officiis blanditiis deleniti doloremque ducimus eius enim fuga harum incidunt ipsum libero non, officiis"
      res.description = """Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid beatae
      blanditiis deleniti doloremque ducimus eius enim fuga harum incidunt ipsum libero non, officiis
      optio repellat tempore voluptate voluptatibus. Atque, corporis.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid beatae
      blanditiis deleniti doloremque ducimus eius enim fuga harum incidunt ipsum libero non, officiis
      optio repellat tempore voluptate voluptatibus. Atque, corporis."""
      res.author = user.login
      res.posted = LocalDateTime.now().minusDays(it.toLong())
      res.tags = "test data generated ${user.login}"
      return@map res
    }
    return res
  }

  fun createUser(num: Int): User {
    val res = User()
    res.login = "user$num"
    res.pass = "\$2a\$10\$vDiXNYgaqz6I6aqt1a16MeBKmaRx4KA1VwMX3bLtt31JjtptGRsoe"
    return res
  }

  fun followers(first: List<User>, second: List<User>) {
    first.forEach { f ->
      second.forEach {
        if (Random().nextBoolean()) {
          f.followers.add(it.login)
          it.following.add(f.login)
        }
      }
    }
    second.forEach { f ->
      first.forEach {
        if (Random().nextBoolean()) {
          f.followers.add(it.login)
          it.following.add(f.login)
        }
      }
    }

  }

}