package com.example.demo.controllers;

import com.example.demo.models.Article;
import com.example.demo.models.Provider;
import com.example.demo.models.Shoes;
import com.example.demo.models.Staff;
import com.example.demo.repo.ArticleRepository;
import com.example.demo.repo.ProviderRepository;
import com.example.demo.repo.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shoes")
public class ShoesController {
    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @GetMapping("/shs")
    public String blogMain(Model model)
    {
        Iterable<Article> articles = articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();
        Iterable<Shoes> shoes = shoesRepository.findAll();
        model.addAttribute("aricles", articles);
        model.addAttribute("shoes", shoes);
        model.addAttribute("providers", providers);
        return "shoes-main";
    }

    @GetMapping("/add")
    public String ShoesAdd(Shoes shoes, Model model)
    {

        Iterable<Article> articles=articleRepository.findAll();
        Iterable<Provider> providers=providerRepository.findAll();

        ArrayList<Article> articleArrayList=new ArrayList<>();
        ArrayList<Provider> providerArrayList=new ArrayList<>();
        for (Provider sub: providers){
            if (sub.getOrganization()==null){
                providerArrayList.add(sub);
            }
        }

        for (Article sub: articles){
            if (sub.getNumber()==null){
                articleArrayList.add(sub);
            }
        }

        model.addAttribute("shoes", shoes);
        model.addAttribute("providers", providers);
        model.addAttribute("articles", articles);

        return "shoes-add";
    }

    @PostMapping("/add")
    public String ShoesAdd(@ModelAttribute("shoes") @Valid Shoes shoes, BindingResult bindingResult, @RequestParam String organization, String number, Model model)
    {
        shoes.setProvider(providerRepository.findByOrganization(organization));
        shoes.setArticle(articleRepository.findByNumber(number));
        if(bindingResult.hasErrors())
        {
            Iterable<Article> articles=articleRepository.findAll();
            Iterable<Provider> providers=providerRepository.findAll();
            model.addAttribute("providers", providers);
            model.addAttribute("articles", articles);

            return "shoes-add";
        }
        shoesRepository.save(shoes);
        return "redirect:/shoes/shs";
    }

    @GetMapping("/filter")
    public String shoesFilter(Model model)
    {
        return "shoes-filter";
    }

    @PostMapping("/filter/result")
    public String shoesResult(@RequestParam String brand, Model model)
    {

        List<Shoes> result = shoesRepository.findByBrandContains(brand);
        List<Shoes> result1 = shoesRepository.findByBrand(brand);
        model.addAttribute("result", result);
        return "shoes-filter";
    }

    @GetMapping("/{id}")
    public String shoesView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Shoes> shoes = shoesRepository.findById(id);
        ArrayList<Shoes> res = new ArrayList<>();
        shoes.ifPresent(res::add);
        model.addAttribute("shoes", res);
        if(!shoesRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "shoes-view";

    }

    @GetMapping("/{id}/edit")
    public String shoesEdit(@PathVariable("id")long id,
                              Model model)
    {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid product Id" + id));
        model.addAttribute("shoes",shoes);
        return "shoes-view";
    }

    @PostMapping("/{id}/edit")
    public String shoesUpdate(@ModelAttribute("shoes")@Valid Shoes shoes, BindingResult bindingResult,
                                @PathVariable("id") long id) {

      shoes.setId(id);
        if (bindingResult.hasErrors()) {
            return "shoes-view";
        }
        shoesRepository.save(shoes);
        return "redirect:/shoes/shs";
    }

    @PostMapping("/{id}/remove")
    public String shoesDelete(@PathVariable("id") long id, Model model){
        Shoes shoes = shoesRepository.findById(id).orElseThrow();
        shoesRepository.delete(shoes);
        return "redirect:/shoes/shs";
    }

}

