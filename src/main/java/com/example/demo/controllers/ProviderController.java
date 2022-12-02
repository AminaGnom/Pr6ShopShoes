package com.example.demo.controllers;


import com.example.demo.models.Provider;
import com.example.demo.models.Shoes;
import com.example.demo.repo.ProviderRepository;
import com.example.demo.repo.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//public class ProviderController {
//    @Autowired
//    public ProviderRepository providerRepository;
//    @Autowired
//    public ShoesRepository shoesRepository;
//
//    @GetMapping("/person")
//    public String Main(Model model){
//        Iterable<Provider> providers = providerRepository.findAll();
//        model.addAttribute("provider",providers);
//        return "person";
//    }
//
//    @PostMapping("/person/add")
//    public String blogPostAdd(@RequestParam String brand, @RequestParam String organization, Model model)
//    {
//        Provider provider = shoesRepository.findByOrganization(organization);
//        Shoes shoes = new Shoes(brand, provider);
//        shoesRepository.save(shoes);
//        return "person";
//    }
//}
