package com.bowlofbits.spring5webfluxrest.repositories;

import com.bowlofbits.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by heindrichpaul on 22/09/2020
 */

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
