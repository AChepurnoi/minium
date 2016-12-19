package com.granium.domain.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Sasha.Chepurnoi on 10.12.16.
 */

class Comment {
  var id: String? = null
  var author: String = ""
  var body: String = ""


}