package com.easybank.accounts.service.client;

import com.easybank.accounts.dto.CardsDTO;
import com.easybank.accounts.dto.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<LoansDTO> fetchLoansDetails(@RequestHeader("easyBank-correlation-id")
                                                          String correlationId, @RequestParam String mobileNumber);
}
