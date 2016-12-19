package com.granium.domain.mongo.repository

import com.granium.domain.mongo.entity.Article
import com.granium.domain.mongo.entity.Draft
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by Sasha.Chepurnoi on 08.12.16.
 */
interface DraftRepository : MongoRepository<Draft, String>{
  fun findDraftByAuthor(author: String): MutableList<Draft>

}
