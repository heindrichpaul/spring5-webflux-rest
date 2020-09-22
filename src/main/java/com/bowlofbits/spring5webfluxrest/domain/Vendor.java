package com.bowlofbits.spring5webfluxrest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by heindrichpaul on 22/09/2020
 */
@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    private String id;
    private String firstname;
    private String lastname;
}
