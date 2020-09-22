package com.bowlofbits.spring5webfluxrest.repositories;

import com.bowlofbits.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by heindrichpaul on 22/09/2020
 */

public interface CategoryRepository extends ReactiveMongoRepository<Category,String> {
}
