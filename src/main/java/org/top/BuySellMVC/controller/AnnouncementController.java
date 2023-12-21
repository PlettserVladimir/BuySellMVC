package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Announcement;
import org.top.BuySellMVC.entity.Category;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.entity.User;
import org.top.BuySellMVC.service.AnnouncementService;
import org.top.BuySellMVC.service.CategoryService;
import org.top.BuySellMVC.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;
    private final UserService userService;
    public AnnouncementController(AnnouncementService announcementService, CategoryService categoryService,
                                  UserService userService){
        this.announcementService = announcementService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private void findProfileByLogin(Principal principal,Model model){
        if (principal !=null) {
            Optional<User> user = userService.findUserByLogin(principal.getName());
            if (user.isPresent()) {
                Profile profile = user.get().getProfile();
                model.addAttribute("autProfile", profile);
            }
        }
    }
    @GetMapping("all")
    public String all(Model model){
        Iterable<Announcement> getAll = announcementService.findAll();
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("announcements",getAll);
        model.addAttribute("categories",categories);
        return "announcement/announcement-list";
    }

    @GetMapping("")
    public String getAll(Model model, Principal principal){
        findProfileByLogin(principal,model);
        Iterable<Announcement> getAll = announcementService.findAll();
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("announcements",getAll);
        model.addAttribute("categories",categories);

        return "announcement/announcement-list";
    }

    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes ra,Principal principal){
        findProfileByLogin(principal,model);
        Optional<Announcement> announcement = announcementService.findById(id);
        if (announcement.isPresent()){
            model.addAttribute("announcement",announcement.get());
            return "announcement/announcement-details";
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Объявление не найдено");
            return "redirect:/announcement";
        }
    }
    @GetMapping("add")
    public String getAddForm(Model model, Principal principal){
        Optional<User> user = userService.findUserByLogin(principal.getName());
        Profile profileUser = user.get().getProfile();
        Announcement announcement = new Announcement();
        announcement.setProfile(profileUser);
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("announcement",announcement);
        model.addAttribute("categories",categories);
        model.addAttribute("autProfile", profileUser);
        return "announcement/form-add-announcement";
    }
    @PostMapping("add")
    public String postAddForm(Announcement announcement, RedirectAttributes ra, @RequestParam MultipartFile previewImage) throws IOException {
        String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
        announcement.setPreviewImageData(previewImageData);
        Optional<Announcement> saved = announcementService.addAnnouncement(announcement);
        if (saved.isPresent()){
            ra.addFlashAttribute(Message.successMessage,"Объявление добавление");
            return "redirect:/announcement";
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Объявление не добавлено");
            return "redirect:/announcement/add";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id,RedirectAttributes ra){
        Optional<Announcement> deleted = announcementService.delete(id);
        if (deleted.isPresent()){
            ra.addFlashAttribute(Message.successMessage,
                    "Объявление "+deleted.get().getTitle()+" успешно удалено");
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Объявление не удалено");
        }
        return "redirect:/announcement/profile/"+deleted.get().getProfile().getId();
    }
    @GetMapping("/update/{id}")
    public String getUpdatedForm(@PathVariable Integer id,Model model,RedirectAttributes ra,Principal principal){
        Optional<Announcement> updated = announcementService.findById(id);
        Iterable<Category> categories = categoryService.findAll();
        if (updated.isPresent()){
            findProfileByLogin(principal,model);
            model.addAttribute("announcement",updated.get());
            model.addAttribute("categories",categories);
            return "announcement/update-announcement-form";
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Объявление не найдено");
            return "redirect:/announcement";
        }
    }
    @PostMapping("/update")
    public String postUpdateForm(Announcement announcement,RedirectAttributes ra,@RequestParam MultipartFile previewImage) throws IOException {
        if (previewImage.getSize()>0) {
            String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
            announcement.setPreviewImageData(previewImageData);
        }else {
            Optional<Announcement> updated = announcementService.findById(announcement.getId());
            announcement.setPreviewImageData(updated.get().getPreviewImageData());
        }
        Optional<Announcement> update = announcementService.update(announcement);
        if (update.isPresent()){
            ra.addFlashAttribute(Message.successMessage,"Объявление успешно изменено");
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Ошибка. Объявление не изменено");
        }
        return "redirect:/announcement/"+announcement.getId();
    }
    @GetMapping("/profile/{id}")
    public String getAllAnnouncementProfile(@PathVariable Integer id, Model model,Principal principal){
        findProfileByLogin(principal,model);
        List<Announcement> announcements = announcementService.findByProfileId(id);
        model.addAttribute("announcements",announcements);
        return "announcement/announcement-profile-list";
    }
    @GetMapping("/filter/{id}")
    public String getAllAnnouncementByCategoryId(@PathVariable Integer id,Model model,Principal principal){
        findProfileByLogin(principal,model);
        Iterable<Category> categories = categoryService.findAll();
        List<Announcement> announcements = announcementService.findByCategoryId(id);
        model.addAttribute("categories",categories);
        model.addAttribute("announcements",announcements);
        return "announcement/announcement-list";
    }

    @GetMapping("buy/{idAnnouncement}")
    public String buy(@PathVariable Integer idAnnouncement, Principal principal, RedirectAttributes ra){
        Optional<User> user = userService.findUserByLogin(principal.getName());
        Profile profileUser = user.get().getProfile();
        Announcement announcement = announcementService.findById(idAnnouncement).get();
        boolean isBuy = announcementService.buy(profileUser,announcement);
        System.out.println(isBuy);
        if (isBuy){
            ra.addFlashAttribute(Message.successMessage,"Успешно");
        }else {
            System.out.println(isBuy);
            ra.addFlashAttribute(Message.errorMessage,"Недостаточно средств");
        }
        return "redirect:/announcement/" + idAnnouncement;
    }
}
