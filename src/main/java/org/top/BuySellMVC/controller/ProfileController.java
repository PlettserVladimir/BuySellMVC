package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.service.ProfileService;

import java.util.Optional;

@Controller
@RequestMapping("user-profile")
public class ProfileController {

    private final ProfileService profileService;
    private final String successMessage = "successMessage";
    private final String errorMessage = "errorMessage";


    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }
    //получить все записи
    @GetMapping("")
    public String getAll(Model model){
        Iterable<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles",profiles);
        return "profile/profile-list";
    }
    //Показать детали записи
    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        Optional<Profile> profile = profileService.findById(id);
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
        Optional<Profile> deleted = profileService.deleteProfile(id);
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
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        return "profile/form-reg-profile";
    }

    @PostMapping("add")
    public String postAddForm(Profile profile, RedirectAttributes redirectAttributes){
        Optional<Profile> saved = profileService.addProfile(profile);
        if (saved.isPresent()){
            redirectAttributes.addFlashAttribute(successMessage,"Пользователь "+ profile.getName()+" создан");
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Пользователь "+ profile.getName()+" не создан");
        }
        return "redirect:/user-profile/add";
    }


    //Редактирование профиль

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes){
        Optional<Profile> updated = profileService.findById(id);
        if (updated.isPresent()){
            model.addAttribute("profile", updated.get());
            return "profile/update-profile-form";
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Пользователь не найден");
            return "redirect:/user-profile";
        }
    }
    @PostMapping("/update")
    public String postUpdateForm(Profile profile, RedirectAttributes redirectAttributes){
        Optional<Profile> updated = profileService.updateProfile(profile);
        if (updated.isPresent()){
            redirectAttributes.addFlashAttribute(successMessage,"Успешно сохранено");
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Изменения не сохранены");
        }
        return "redirect:/user-profile";
    }

}
