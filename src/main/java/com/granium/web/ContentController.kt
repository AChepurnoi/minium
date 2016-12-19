package com.granium.web

import com.granium.domain.psql.entity.User
import com.granium.service.ArticleService
import com.granium.service.StatisticService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

/**
 * Created by Sasha.Chepurnoi on 08.12.16.
 */
@Controller
open class ContentController : AbstractController() {

  @Autowired lateinit var articleService: ArticleService
  @Autowired lateinit var statsService: StatisticService

  @RequestMapping(path = arrayOf("/"), method = arrayOf(RequestMethod.GET))
  fun index() = redirect("feed")

  @RequestMapping(path = arrayOf("/feed"), method = arrayOf(RequestMethod.GET))
  fun feed(@RequestParam(required = false, defaultValue = "0") page: Int,
           @AuthenticationPrincipal user: User,
           modelMap: ModelMap): String {
    val records = articleService.loadFeed(user, page)
    modelMap.apply {
      addAttribute("page", page)
      addAttribute("articles", records)
    }
    return "index"
  }


  @RequestMapping(path = arrayOf("/search"), method = arrayOf(RequestMethod.GET))
  fun search(@RequestParam(required = false, defaultValue = "") query: String,
             @RequestParam(required = false, defaultValue = "0") page: Int,
             modelMap: ModelMap): String {
    if(query.isEmpty())return redirect("feed")
    val records = if (isTagSearch(query.trim())) articleService.searchTags(query, page) else articleService.searchContent(query, page)
    modelMap.apply {
      addAttribute("page", page)
      addAttribute("articles", records)
    }
    return "index"
  }

  @RequestMapping(path = arrayOf("/stats"), method = arrayOf(RequestMethod.GET))
  fun stats(@RequestParam(required = false, defaultValue = "") query: String,
            @RequestParam(required = false, defaultValue = "0") page: Int,
            modelMap: ModelMap): String {
    val topUser = statsService.topUsers()
    val topArticles = statsService.topArticles()
    val (usersCount, articleCount) = statsService.getCounters()
    modelMap.apply {
      addAttribute("users", topUser)
      addAttribute("articles", topArticles)
      addAttribute("usersCount", usersCount).addAttribute("articleCount", articleCount)
    }
    return "stats"
  }



  
  private fun isTagSearch(query: String): Boolean {
    val tags = query.split(" ").filter { it.first() == '#' }
    return tags.isNotEmpty()
  }

}