package com.service.freeblog_batch.web.domain.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialAddress {
    private String address;
    private String github;
    private String twitter;
    private String instagram;
}
