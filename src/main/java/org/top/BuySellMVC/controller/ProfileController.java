package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.entity.User;
import org.top.BuySellMVC.form.UserRegistrationsForm;
import org.top.BuySellMVC.service.ProfileService;
import org.top.BuySellMVC.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }
    //получить все записи
    @GetMapping("")
    public String getAll(Model model,Principal principal){
        Iterable<Profile> profiles = profileService.findAll();
        findProfileByLogin(principal,model);
        model.addAttribute("profiles",profiles);
        return "profile/profile-list";
    }
    //Показать детали записи
    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, Principal principal){
        Optional<Profile> profile = profileService.findById(id);
        findProfileByLogin(principal,model);
        if (profile.isPresent()){
            model.addAttribute("profile",profile.get());
            return "profile/profile-details";
        }else {
            redirectAttributes.addFlashAttribute(Message.errorMessage,"Профиль с ИД "+id+"не найден");
            return "redirect:/profile";
        }
    }
    //Удалить запись
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        Optional<Profile> deleted = profileService.deleteProfile(id);
        if (deleted.isPresent()){
            redirectAttributes.addFlashAttribute(Message.successMessage,
                    "Профиль "+deleted.get().getName()+" успешно удален");
        }else {
            redirectAttributes.addFlashAttribute(Message.errorMessage,"Профиль не удален");
        }
        return "redirect:/profile";
    }

    //Добавить запись
    @GetMapping("add")
    public String getAddForm(Model model,Principal principal){
        UserRegistrationsForm newProfile = new UserRegistrationsForm();
        findProfileByLogin(principal,model);
        model.addAttribute("newProfile", newProfile);
        return "profile/form-reg-profile";
    }

    @PostMapping("add")
    public String postAddForm(UserRegistrationsForm newProfile , RedirectAttributes redirectAttributes){
        boolean saved = userService.register(newProfile);
        if (saved){
            redirectAttributes.addFlashAttribute(Message.successMessage,"Профиль "+ newProfile.getName()+" создан");
        }else {
            redirectAttributes.addFlashAttribute(Message.errorMessage,"Профиль "+ newProfile.getName()+" не создан");
        }
        return "redirect:/profile";
    }


    //Редактирование профиль

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes,Principal principal){
        Optional<Profile> updated = profileService.findById(id);
        findProfileByLogin(principal,model);
        if (updated.isPresent()){
            model.addAttribute("profile", updated.get());
            return "profile/update-profile-form";
        }else {
            redirectAttributes.addFlashAttribute(Message.errorMessage,"Пользователь не найден");
            return "redirect:/profile";
        }
    }
    @PostMapping("/update")
    public String postUpdateForm(Profile profile, RedirectAttributes redirectAttributes){
        Optional<Profile> updated = profileService.updateProfile(profile);
        if (updated.isPresent()){
            redirectAttributes.addFlashAttribute(Message.successMessage,"Успешно сохранено");
        }else {
            redirectAttributes.addFlashAttribute(Message.errorMessage,"Изменения не сохранены");
        }
        return "redirect:/profile";
    }

    @GetMapping("replenishment")
    public String getReplenishmentForm(){
        return "profile/replenishment-form";
    }
    @PostMapping("replenishment")
    public String postReplenishmentForm(@RequestParam Integer summa, RedirectAttributes ra,Principal principal){
        Optional<User> user = userService.findUserByLogin(principal.getName());
        if (user.isPresent()) {
            Profile profile = user.get().getProfile();
            if (profileService.replenishmentOfBalance(profile,summa)) {
                ra.addFlashAttribute(Message.successMessage, "Баланс успешно пополнен на сумму " + summa);
            }
        }
        ra.addFlashAttribute(Message.errorMessage,"Баланс не пополнен");
        return "redirect:/profile/replenishment";
    }

    private void findProfileByLogin(Principal principal, Model model){
        Optional<User> user = userService.findUserByLogin(principal.getName());
        if (user.isPresent()) {
            Profile profile = user.get().getProfile();
            model.addAttribute("autProfile", profile);
        }
    }

}
