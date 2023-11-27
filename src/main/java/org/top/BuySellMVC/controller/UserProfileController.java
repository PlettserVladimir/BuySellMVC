package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.UserProfile;
import org.top.BuySellMVC.service.UserProfileService;

import java.util.Optional;

@Controller
@RequestMapping("user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final String successMessage = "successMessage";
    private final String errorMessage = "errorMessage";


    public UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }
    //получить все записи
    @GetMapping("")
    public String getAll(Model model){
        Iterable<UserProfile> profiles = userProfileService.findAll();
        model.addAttribute("profiles",profiles);
        return "profile/profile-list";
    }
    //Показать детали записи
    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        Optional<UserProfile> profile = userProfileService.findById(id);
        if (profile.isPresent()){
            model.addAttribute("profile",profile.get());
            return "profile/profile-details";
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Пользователь с ИД "+id+"не найден");
            return "redirect:/user-profile";
        }
    }
    //Удалить запись
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        Optional<UserProfile> deleted = userProfileService.deleteUserProfile(id);
        if (deleted.isPresent()){
            redirectAttributes.addFlashAttribute(successMessage,
                    "Пользователь "+deleted.get().getName()+" успешно удален");
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Пользователь не найден");
        }
        return "redirect:/user-profile";
    }

    //Добавить запись
    @GetMapping("add")
    public String getAddForm(Model model){
        UserProfile userProfile = new UserProfile();
        model.addAttribute("profile",userProfile);
        return "profile/form-reg-user";
    }

    @PostMapping("add")
    public String postAddForm(UserProfile userProfile,RedirectAttributes redirectAttributes){
        Optional<UserProfile> saved = userProfileService.addUserProfile(userProfile);
        if (saved.isPresent()){
            redirectAttributes.addFlashAttribute(successMessage,"Пользователь "+userProfile.getName()+" создан");
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Пользователь "+userProfile.getName()+" не создан");
        }
        return "redirect:/user-profile/add";
    }


}
