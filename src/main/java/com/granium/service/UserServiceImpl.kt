package com.granium.service

import com.granium.domain.psql.entity.User
import com.granium.domain.psql.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
open class UserServiceImpl : UserService {
  @Autowired lateinit var userRepository: UserRepository


  @Cacheable(cacheNames = arrayOf("user"),key = "#username")
  override fun loadUserByUsername(username: String?): User = userRepository.findByLogin(username) ?: throw UsernameNotFoundException("No username $username")

  override fun isExist(username: String): Boolean {
    val res = userRepository.findByLogin(username)
    return res != null
  }

  @CacheEvict(cacheNames = arrayOf("user"),allEntries = true)
  override fun save(user: User): User {
    user.pass = BCrypt.hashpw(user.password, BCrypt.gensalt())
    return userRepository.save(user)
  }

  @CacheEvict(cacheNames = arrayOf("user"),allEntries = true)
  override fun followToggle(target: User, follower: User) {
    if (target.username == follower.username) throw RuntimeException()

    if (target.followers.contains(follower.username)) {
      target.followers.remove(follower.username)
      follower.following.remove(target.username)
    } else {
      target.followers.add(follower.username)
      follower.following.add(target.username)
    }
    userRepository.save(listOf(target, follower))
  }
}