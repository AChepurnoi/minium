package com.granium.web

import com.granium.domain.psql.entity.User
import com.granium.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Created by Sasha.Chepurnoi on 07.12.16.
 */

@Controller
open class UserController : AbstractController() {

  @Autowired lateinit var userService: UserService


  @RequestMapping(path = arrayOf("login", "login/"), method = arrayOf(RequestMethod.GET))
  fun loginPage() = LOGIN_PAGE

  @ModelAttribute("user") fun userProto() = User()

  @RequestMapping(path = arrayOf("register", "register/"), method = arrayOf(RequestMethod.GET))
  fun registerPage() = REGISTER_PAGE

  @RequestMapping(path = arrayOf("register", "register/"), method = arrayOf(RequestMethod.POST))
  fun registerUser(@Valid @ModelAttribute newUser: User, result: BindingResult): String {
    if (result.hasErrors()) return REGISTER_PAGE
    if (userService.isExist(newUser.login)) return REGISTER_PAGE
    userService.save(newUser)
    return redirect(LOGIN_PAGE)
  }

  @RequestMapping(path = arrayOf("user/{username}"), method = arrayOf(RequestMethod.GET))
  fun userPage(@PathVariable("username") username: String,
               modelMap: ModelMap): String {
    val user = userService.loadUserByUsername(username)
    modelMap.addAttribute("user", user)
    return PROFILE_PAGE
  }

  @RequestMapping(path = arrayOf("user/{username}/follow"), method = arrayOf(RequestMethod.POST))
  @ResponseBody
  fun toggleFollowing(@PathVariable("username") username: String,
                      @AuthenticationPrincipal user: User): ResponseEntity<Any> {
    val target = userService.loadUserByUsername(username) as User
    userService.followToggle(target, user)
    return ResponseEntity(HttpStatus.OK)
  }

}