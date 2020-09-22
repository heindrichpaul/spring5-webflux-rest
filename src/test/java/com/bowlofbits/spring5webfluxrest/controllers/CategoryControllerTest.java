package com.bowlofbits.spring5webfluxrest.controllers;

import com.bowlofbits.spring5webfluxrest.domain.Category;
import com.bowlofbits.spring5webfluxrest.repositories.CategoryRepository;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private WebTestClient webTestClient;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getCategories() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()
                        )
                );

        webTestClient.get().uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Category.builder().description("Cat1").build()
                        )
                );

        webTestClient.get().uri("/api/v1/categories/1dafdas1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(
                        Category.builder().description("Cat1").build()
                        )
                );

        Mono<Category> categoryMono = Mono.just(Category.builder().id("12245t").description("Fruit").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(
                        Category.builder().description("Cat1").build()
                        )
                );

        Mono<Category> categoryMono = Mono.just(Category.builder().id("12245t").description("Fruit").build());

        webTestClient.put()
                .uri("/api/v1/categories/124rtgs")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isAccepted();

    }

    @Test
    void patchCategory() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(
                        Category.builder().description("Cat1").build()
                        )
                );

        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Category.builder().description("Cat1").build()
                        )
                );

        Mono<Category> categoryMono = Mono.just(Category.builder().id("12245t").description("Fruit").build());

        webTestClient.patch()
                .uri("/api/v1/categories/124rtgs")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isAccepted();
        verify(categoryRepository).save(any());
    }

    @Test
    void patchNoChangeCategory() {

        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(
                        Category.builder().build()
                        )
                );

        Mono<Category> categoryMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri("/api/v1/categories/124rtgs")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isAccepted();
        verify(categoryRepository,never()).save(any());
    }
}