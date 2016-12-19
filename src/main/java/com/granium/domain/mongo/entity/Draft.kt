package com.granium.domain.mongo.entity

import org.springframework.data.annotation.Id
import javax.validation.constraints.Size


/**
 * Created by Sasha.Chepurnoi on 08.12.16.
 */
class Draft {

  @Id
  var id: String? = null
  @Size(min = 4, max = 50,message = "Title too short")
  var title: String = ""
  var tags: String = ""
  @Size(min = 4, max = 100,message = "Short description too short")
  var short: String = ""
  @Size(min = 5, message = "Description too short")
  var description: String = ""
  var author: String = ""

}