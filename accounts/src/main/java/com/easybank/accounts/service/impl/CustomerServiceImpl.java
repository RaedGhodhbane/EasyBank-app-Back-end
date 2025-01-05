package com.easybank.accounts.service.impl;

import com.easybank.accounts.dto.AccountsDTO;
import com.easybank.accounts.dto.CardsDTO;
import com.easybank.accounts.dto.CustomerDetailsDTO;
import com.easybank.accounts.dto.LoansDTO;
import com.easybank.accounts.entity.Accounts;
import com.easybank.accounts.entity.Customer;
import com.easybank.accounts.exception.ResourceNotFoundException;
import com.easybank.accounts.mapper.AccountsMapper;
import com.easybank.accounts.mapper.CustomerMapper;
import com.easybank.accounts.repository.AccountsRepository;
import com.easybank.accounts.repository.CustomerRepository;
import com.easybank.accounts.service.ICustomerService;
import com.easybank.accounts.service.client.CardsFeignClient;
import com.easybank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId",customer.getCustomerId().toString())
        );

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer,new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts,new AccountsDTO()));

        ResponseEntity<LoansDTO> loansDTOResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);
        customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());

        return customerDetailsDTO;
    }
}
