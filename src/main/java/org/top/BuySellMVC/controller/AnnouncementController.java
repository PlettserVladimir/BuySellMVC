package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Announcement;
import org.top.BuySellMVC.entity.Category;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.service.AnnouncementService;
import org.top.BuySellMVC.service.CategoryService;
import org.top.BuySellMVC.service.ProfileService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;
    private final ProfileService profileService;
    public AnnouncementController(AnnouncementService announcementService, CategoryService categoryService,
                                  ProfileService profileService){
        this.announcementService = announcementService;
        this.categoryService = categoryService;
        this.profileService = profileService;
    }

    @GetMapping("")
    public String getAll(Model model){
        Iterable<Announcement> getAll = announcementService.findAll();
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("announcements",getAll);
        model.addAttribute("categories",categories);
        return "announcement/announcement-list";
    }

    @GetMapping("/{id}")
    public String getId(@PathVariable Integer id, Model model, RedirectAttributes ra){
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
    public String getAddForm(Model model){
        Announcement announcement = new Announcement();
        Iterable<Category> categories = categoryService.findAll();
        Iterable<Profile> profiles = profileService.findAll();
        model.addAttribute("announcement",announcement);
        model.addAttribute("categories",categories);
        model.addAttribute("profiles",profiles);
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
        return "redirect:/announcement";
    }
    @GetMapping("/update/{id}")
    public String getUpdatedForm(@PathVariable Integer id,Model model,RedirectAttributes ra){
        Optional<Announcement> updated = announcementService.findById(id);
        Iterable<Category> categories = categoryService.findAll();
        if (updated.isPresent()){
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
        String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
        announcement.setPreviewImageData(previewImageData);
        Optional<Announcement> update = announcementService.update(announcement);
        if (update.isPresent()){
            ra.addFlashAttribute(Message.successMessage,"Объявление успешно изменено");
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Ошибка. Объявление не изменено");
        }
        return "redirect:/announcement";
    }
    @GetMapping("/profile/{id}")
    public String getAllAnnouncementProfile(@PathVariable Integer id, Model model){
        List<Announcement> announcements = announcementService.findByProfileId(id);
        model.addAttribute("announcements",announcements);
        return "announcement/announcement-profile-list";
    }
    @GetMapping("/filter/{id}")
    public String getAllAnnouncementByCategoryId(@PathVariable Integer id,Model model){
        Iterable<Category> categories = categoryService.findAll();
        List<Announcement> announcements = announcementService.findByCategoryId(id);
        model.addAttribute("categories",categories);
        model.addAttribute("announcements",announcements);
        return "announcement/announcement-list";
    }
}
