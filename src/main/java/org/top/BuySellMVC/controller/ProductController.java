package org.top.BuySellMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.BuySellMVC.entity.Product;
import org.top.BuySellMVC.service.ProductService;

import java.util.Optional;

@Controller
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;
    private final String successMessage = "successMessage";
    private final String errorMessage = "errorMessage";

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    //Добавление товара
    @GetMapping("/add")
    public String getAdd(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "product/form-add-product";
    }
    @PostMapping("add")
    public String postAdd(Product product, RedirectAttributes redirectAttributes){
        Optional<Product> saved = productService.add(product);
        if (saved.isPresent()){
            redirectAttributes.addFlashAttribute(successMessage,"Товар добавлен");
        }else {
            redirectAttributes.addFlashAttribute(errorMessage,"Ошибка.Товар не добавлен");
        }return "redirect:/product";
    }

    @GetMapping("")
    public String getAll(Model model){
        Iterable<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "product/product-list";
    }

}
