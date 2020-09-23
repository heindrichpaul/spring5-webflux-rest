package com.bowlofbits.spring5webfluxrest.controllers;

import com.bowlofbits.spring5webfluxrest.domain.Vendor;
import com.bowlofbits.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private WebTestClient webTestClient;
    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorController vendorController;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getVendors() {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder().firstname("John").lastname("Doe").build(),
                        Vendor.builder().firstname("Jane").lastname("Doe").build()
                        )
                );

        webTestClient.get().uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Vendor.builder().firstname("John").lastname("Doe").build()
                        )
                );

        webTestClient.get().uri("/api/v1/vendors/1dafdas1")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void createVendor() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(
                        Vendor.builder().firstname("Fred").build()
                        )
                );

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().id("12245t").firstname("Fred").lastname("Flinstone").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateVendor() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(
                        Vendor.builder().firstname("Fred").build()
                        )
                );

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().id("12245t").firstname("Fred").lastname("Flinstone").build());

        webTestClient.put()
                .uri("/api/v1/vendors/adfsa")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    void patchVendor() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(
                        Vendor.builder().firstname("Fred").build()
                        )
                );

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Vendor.builder().firstname("John").lastname("Doe").build()
                        )
                );

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().id("12245t").firstname("Fred").lastname("Flinstone").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/adfsa")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(vendorRepository,times(2)).save(any());
    }

    @Test
    void patchNoChangeVendor() {

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Vendor.builder().firstname("John").lastname("Doe").build()
                        )
                );

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstname("John").lastname("Doe").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/adfsa")
                .body(vendorMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(vendorRepository,never()).save(any());
    }
}