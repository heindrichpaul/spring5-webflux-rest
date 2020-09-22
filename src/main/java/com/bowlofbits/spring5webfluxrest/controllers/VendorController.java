package com.bowlofbits.spring5webfluxrest.controllers;

import com.bowlofbits.spring5webfluxrest.domain.Vendor;
import com.bowlofbits.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by heindrichpaul on 22/09/2020
 */

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @ResponseBody
    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> getVendors(){
        return vendorRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseBody
    @PostMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @ResponseBody
    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @ResponseBody
    @PatchMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();
        if (!StringUtils.isEmpty(vendor.getFirstname()) && !foundVendor.getFirstname().equals(vendor.getFirstname())) {
            foundVendor.setFirstname(vendor.getFirstname());
            vendorRepository.save(foundVendor);
        }
        if (!StringUtils.isEmpty(vendor.getLastname()) && !foundVendor.getLastname().equals(vendor.getLastname())) {
            foundVendor.setFirstname(vendor.getLastname());
            vendorRepository.save(foundVendor);
        }
        return Mono.just(foundVendor);
    }
}
