package com.granium.service

import com.granium.domain.mongo.entity.Draft
import com.granium.domain.mongo.repository.DraftRepository
import com.granium.domain.psql.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
open class DraftServiceImpl : DraftService {

  @Autowired lateinit var draftRepo: DraftRepository

  override fun save(draft: Draft): Draft {
    return draftRepo.save(draft)
  }

  override fun delete(caller: User, draftId: String) {
    val draft = findById(caller, draftId)
    draftRepo.delete(draft)
  }

  override fun findById(caller: User, id: String): Draft {
    val draft = draftRepo.findOne(id) ?: throw RuntimeException()
    if (draft.author != caller.username) throw RuntimeException()
    return draft
  }

  override fun findAll(caller: User): List<Draft> = draftRepo.findDraftByAuthor(caller.username)


  override fun create(author: User, draft: Draft): Draft {
    draft.author = author.login
    return save(draft)
  }
}