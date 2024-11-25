package com.enigma.wmb_api.controller;


import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.dto.request.CustomerRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.CUSTOMER_API)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success Create Customer", customerResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> customerResponseList = customerService.getAllCustomers();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Get All Customers", customerResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Get Customer by Id", customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.updateCustomer(id, customerRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Update Customer", customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Delete Customer by Id", null);
    }
}
