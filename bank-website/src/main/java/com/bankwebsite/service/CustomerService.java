package com.bankwebsite.service;

import com.bankwebsite.model.Customer;
import com.bankwebsite.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }
    public Customer updateCustomer(Long id, Customer customer){
        return customerRepository.findById(id).map(existing ->{
            existing.setFirstName(customer.getFirstName());
            existing.setLastName(customer.getLastName());
            existing.setEmail(customer.getEmail());
            existing.setPhoneNumber(customer.getPhoneNumber());
            return customerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not Found with" + id));
    }
    public void deleteCustomer(Long id){
        if(!customerRepository.existsById(id)){
            throw new RuntimeException("Customer not Found with" + id);
        }
        customerRepository.deleteById(id);
    }
}
