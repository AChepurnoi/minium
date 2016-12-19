package com.granium.web

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Comment
import com.granium.domain.psql.entity.User
import com.granium.service.ArticleService
import com.granium.service.utility.buildComment
import org.omg.CosNaming.NamingContextPackage.NotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.xml.ws.Response

/**
 * Created by Sasha.Chepurnoi on 08.12.16.
 */
@Controller
class ArticleController : AbstractController() {

  @Autowired lateinit var articleService: ArticleService

  @RequestMapping(path = arrayOf("/article"), method = arrayOf(RequestMethod.GET))
  fun listArticles(@AuthenticationPrincipal user: User,
                   modelMap: ModelMap): String {

    val articles = articleService.findAll(user)
    modelMap.addAttribute("articles", articles)
    return LIST_ARTICLES_PAGE
  }

  @RequestMapping(path = arrayOf("/article/{articleId}"), method = arrayOf(RequestMethod.GET))
  fun getArticle(@PathVariable("articleId") articleId: String,
                 modelMap: ModelMap): String {

    val article = articleService.findById(articleId)
    modelMap.addAttribute("article", article)
    return ARTICLE_PAGE
  }

  @RequestMapping(path = arrayOf("/article/{articleId}/delete"), method = arrayOf(RequestMethod.DELETE))
  @ResponseBody
  fun deleteArticle(@PathVariable("articleId") articleId: String,
                    @AuthenticationPrincipal user: User): ResponseEntity<Any> {
    articleService.delete(user, articleId)
    return ResponseEntity(HttpStatus.OK)
  }

  @RequestMapping(path = arrayOf("/article/{articleId}/comment"), method = arrayOf(RequestMethod.POST))
  @ResponseBody
  fun comment(@PathVariable("articleId") articleId: String,
              @RequestParam commentBody: String,
              @AuthenticationPrincipal user: User): ResponseEntity<Any> {

    val comment = buildComment(commentBody, user.login)
    val article = articleService.findById(articleId)!!
    articleService.comment(article, comment)
    return ResponseEntity(HttpStatus.OK)
  }

  @RequestMapping(path = arrayOf("/article/{articleId}/comment/{commentId}"), method = arrayOf(RequestMethod.DELETE))
  @ResponseBody
  fun deleteComment(@PathVariable("articleId") articleId: String,
                    @PathVariable("commentId") commentId: String,
                    @AuthenticationPrincipal user: User): ResponseEntity<Any> {

    val article = articleService.findById(articleId)!!
    val comment = article.comments.find { it.id == commentId } ?: throw RuntimeException()
    articleService.removeComment(article, comment, user)
    return ResponseEntity(HttpStatus.OK)
  }

  @RequestMapping(path = arrayOf("/article/{articleId}/like"), method = arrayOf(RequestMethod.POST))
  @ResponseBody
  fun toggleFollowing(@PathVariable("articleId") articleId: String,
                      @AuthenticationPrincipal user: User): ResponseEntity<Any> {
    val article = articleService.findById(articleId) ?: throw RuntimeException()
    articleService.like(article, user)
    return ResponseEntity(HttpStatus.OK)
  }




}