package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.form.UserRegistrationsForm;
import org.top.BuySellMVC.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Регистрация
    @GetMapping("register")
    public String getRegistrationForm(Model model){
        UserRegistrationsForm userRegistrationsForm = new UserRegistrationsForm();
        model.addAttribute("userRegistrationsForm",userRegistrationsForm);
        return "user/registration-form";
    }
    @PostMapping("register")
    public String postRegistrationForm(UserRegistrationsForm userRegistrationsForm, RedirectAttributes ra){
        userRegistrationsForm.setRole("USER");
        if (userService.register(userRegistrationsForm)){
            return "redirect:/login";
        }
        return "redirect:/user/register";
    }
}
