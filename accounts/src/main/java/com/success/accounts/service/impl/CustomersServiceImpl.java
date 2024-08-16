package com.success.accounts.service.impl;

import com.success.accounts.dto.AccountsDto;
import com.success.accounts.dto.CardsDto;
import com.success.accounts.dto.CustomerDetailsDto;
import com.success.accounts.dto.LoansDto;
import com.success.accounts.entity.Accounts;
import com.success.accounts.entity.Customer;
import com.success.accounts.exception.ResourceNotFoundException;
import com.success.accounts.mapper.AccountsMapper;
import com.success.accounts.repository.AccountRepository;
import com.success.accounts.repository.CustomerRepository;
import com.success.accounts.service.CustomersService;
import com.success.accounts.service.client.CardsFeignClient;
import com.success.accounts.service.client.LoansFeignClient;
import com.success.accounts.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CustomersServiceImpl implements CustomersService {
    @Autowired
    private AccountRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CardsFeignClient cardsFeignClient;
    @Autowired
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
