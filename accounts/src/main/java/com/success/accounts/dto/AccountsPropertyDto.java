package com.success.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public class AccountsPropertyDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
