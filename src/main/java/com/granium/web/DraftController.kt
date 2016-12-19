package com.granium.web

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Draft
import com.granium.domain.psql.entity.User
import com.granium.service.ArticleService
import com.granium.service.DraftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Created by Sasha.Chepurnoi on 08.12.16.
 */

@Controller
class DraftController : AbstractController() {

  @Autowired lateinit var draftService: DraftService
  @Autowired lateinit var articleService: ArticleService
  @ModelAttribute("draft") fun draftModel() = Draft()


  @RequestMapping(path = arrayOf("/draft/create"), method = arrayOf(RequestMethod.GET))
  fun createDraftPage() = CREATE_DRAFT_PAGE

  @RequestMapping(path = arrayOf("/draft/create"), method = arrayOf(RequestMethod.POST))
  fun createDraft(@Valid draft: Draft,
                  @AuthenticationPrincipal user: User,
                  result: BindingResult): String {
    if(result.hasErrors()) return CREATE_DRAFT_PAGE
    draftService.create(user, draft)
    return redirect(LIST_DRAFTS_URL)
  }

  @RequestMapping(path = arrayOf("/draft"), method = arrayOf(RequestMethod.GET))
  fun listDrafts(@AuthenticationPrincipal user: User,
                 modelMap: ModelMap): String {

    val drafts = draftService.findAll(user)
    modelMap.addAttribute("drafts", drafts)
    return LIST_DRAFTS_PAGE
  }

  @RequestMapping(path = arrayOf("/draft/{draftId}"), method = arrayOf(RequestMethod.GET))
  fun getDraft(@PathVariable("draftId") draftId: String,
               @AuthenticationPrincipal user: User,
               modelMap: ModelMap): String {

    val draft = draftService.findById(user, draftId)
    modelMap.addAttribute("draft", draft)
    return DRAFT_PAGE
  }

  @RequestMapping(path = arrayOf("/draft/{draftId}/delete"), method = arrayOf(RequestMethod.POST))
  fun deleteDraft(@PathVariable("draftId") draftId: String,
                  @AuthenticationPrincipal user: User): ResponseEntity<Any> {
    draftService.delete(user, draftId)
    return ResponseEntity(HttpStatus.OK)
  }

  @RequestMapping(path = arrayOf("/draft/publish"), method = arrayOf(RequestMethod.POST))
  @ResponseBody
  fun publishDraft(@Valid @ModelAttribute draft: Draft,
                   @AuthenticationPrincipal user: User): ResponseEntity<Any> {
    articleService.publish(draft, user)
    draftService.delete(user, draft.id!!)
    return ResponseEntity(HttpStatus.OK)
  }


}