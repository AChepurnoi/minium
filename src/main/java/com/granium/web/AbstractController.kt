package com.granium.web

import com.granium.domain.psql.entity.User
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ModelAttribute

/**
 * Created by Sasha.Chepurnoi on 07.12.16.
 */

abstract class AbstractController {
  val LOGIN_PAGE = "login"
  val REGISTER_PAGE = "register"
  val CREATE_DRAFT_URL = "draft/create"
  val CREATE_DRAFT_PAGE = "draft/create"
  val LIST_ARTICLES_PAGE = "article/list"
  val LIST_DRAFTS_URL = "draft/"
  val LIST_DRAFTS_PAGE = "draft/list"
  val ARTICLE_PAGE = "article/page"
  val DRAFT_PAGE = "draft/page"

  val DRAFT_PAGE_URL = "draft"
  val ARTICLE_PAGE_URL = "article"

  val PROFILE_PAGE = "profilePage"
  val PAGE_LIMIT = 50

  @ModelAttribute("principal") fun principal(): User? {
    val principal = SecurityContextHolder.getContext().authentication?.principal
    if (principal is User) return principal
    else return null
  }

  fun redirect(page: String) = "redirect:/$page"
}