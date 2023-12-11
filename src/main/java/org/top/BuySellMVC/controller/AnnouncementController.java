package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Announcement;
import org.top.BuySellMVC.service.AnnouncementService;

import java.util.Optional;

@Controller
@RequestMapping("announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }

    @GetMapping("")
    public String getAll(Model model){
        Iterable<Announcement> getAll = announcementService.findAll();
        model.addAttribute("announcements",getAll);
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
        model.addAttribute("announcement",announcement);
        return "announcement/form-add-announcement";
    }
    @PostMapping("add")
    public String postAddForm(Announcement announcement,RedirectAttributes ra){
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
        if (updated.isPresent()){
            model.addAttribute("announcement",updated.get());
            return "announcement/update-announcement-form";
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Объявление не найдено");
            return "redirect:/announcement";
        }
    }
    @PostMapping("/update")
    public String postUpdateForm(Announcement announcement,Model model,RedirectAttributes ra){
        Optional<Announcement> update = announcementService.update(announcement);
        if (update.isPresent()){
            ra.addFlashAttribute(Message.successMessage,"Объявление успешно изменено");
        }else {
            ra.addFlashAttribute(Message.errorMessage,"Ошибка. Объявление не изменено");
        }
        return "redirect:/announcement";
    }
}
