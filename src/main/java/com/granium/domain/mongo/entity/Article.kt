package com.granium.domain.mongo.entity

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.validation.constraints.Size

/**
 * Created by Sasha.Chepurnoi on 06.12.16.
 */
@Document
class Article {

  @Id
  var id: String? = null
  var title: String = ""
  var short: String = ""
  var description: String = ""
  var liked: MutableSet<String> = mutableSetOf()
  var tags: String = ""
  var comments: MutableList<Comment> = mutableListOf()
  var author: String = ""
  var posted: LocalDateTime = LocalDateTime.MIN
  var likes: Int = 0

  @PrePersist
  @PreUpdate
      //    @TODO Find out best way to solve this
  fun prepersist() {
    likes = liked.size
  }


}