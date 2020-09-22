package com.bowlofbits.spring5webfluxrest.bootstrap;

import com.bowlofbits.spring5webfluxrest.domain.Category;
import com.bowlofbits.spring5webfluxrest.domain.Vendor;
import com.bowlofbits.spring5webfluxrest.repositories.CategoryRepository;
import com.bowlofbits.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by heindrichpaul on 22/09/2020
 */

@Component
public class Bootstrap implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }

    private void loadVendors() {
        if(vendorRepository.count().block() == 0) {
            vendorRepository.save(Vendor.builder().firstname("Mike").lastname("Myers").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Roger").lastname("Blocker").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Freddy").lastname("Mur").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Rest").lastname("System").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Girr").lastname("Bytes").build()).block();
        }
        System.out.println("Vendors Loaded: " + vendorRepository.count().block());
    }

    private void loadCategories() {
        if(categoryRepository.count().block() == 0) {
            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Dried").build()).block();
            categoryRepository.save(Category.builder().description("Fresh").build()).block();
            categoryRepository.save(Category.builder().description("Exotic").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
        }

        System.out.println("Categories Loaded: " + categoryRepository.count().block());
    }
}
