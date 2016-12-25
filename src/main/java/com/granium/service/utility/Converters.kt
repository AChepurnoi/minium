package com.granium.service.utility

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Comment
import com.granium.domain.mongo.entity.Draft
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Sasha.Chepurnoi on 10.12.16.
 */


fun convert(draft: Draft): Article {
  val res = Article().apply {
    id = draft.id
    title = draft.title
    tags = draft.tags
    short = draft.short
    description = draft.description
    author = draft.author
    posted = LocalDateTime.now()
  }
  return res
}

fun buildComment(commentBody: String, commentAuthor: String) = Comment().apply {
  id = UUID.randomUUID().toString()
  body = commentBody
  author = commentAuthor
}

