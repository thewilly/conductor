package io.github.thewilly.conductor.server.controllers.web

import io.github.thewilly.conductor.server.services.experimental.UsersService
import io.github.thewilly.conductor.server.types.LoginInfo
import io.github.thewilly.conductor.server.types.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class UsersController {

    @Autowired
    internal var usersService: UsersService? = null

    @RequestMapping("/")
    fun index(): String {
        return "login"
    }

    @RequestMapping("/login", method = [RequestMethod.POST])
    fun login(model: Model, @ModelAttribute("LoginInfo") loginInfo: LoginInfo,
              result: BindingResult, response: HttpServletResponse): String {
        val authenticated = usersService!!.auth(loginInfo.email, loginInfo.password)
        if (authenticated != null) {
            val userSession = Cookie("bmnUserEmail", authenticated.email)
            userSession.maxAge = 1000
            response.addCookie(userSession)

            return "redirect:/devices"
        }
        return "login"
    }
}
