package com.success.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record AccountPropertyDto(String message, Map<String, String> contact, List<String> onCallSupport) {

}