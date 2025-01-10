package com.easybank.accounts.service;

import com.easybank.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
