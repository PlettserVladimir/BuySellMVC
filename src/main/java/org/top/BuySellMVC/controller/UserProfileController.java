package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.UserProfile;
import org.top.BuySellMVC.service.UserProfileService;

import java.util.Optional;

@Controller
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }
    @GetMapping("")
    public String getAll(Model model){
        Iterable<UserProfile> profiles = userProfileService.findAll();
        model.addAttribute("profiles",profiles);
        return "profile/profile-list";
    }

    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        Optional<UserProfile> profile = userProfileService.findById(id);
        if (profile.isPresent()){
            model.addAttribute("profile",profile.get());
            return "profile/profile-details";
        }else {
            redirectAttributes.addAttribute("errorMessage","Пользователь с ИД "+id+"не найден");
            return "redirect:/user-profile";
        }
    }


}
