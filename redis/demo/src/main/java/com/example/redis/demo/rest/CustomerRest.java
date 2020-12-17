package com.example.redis.demo.rest;

import java.util.List;
import java.util.Optional;

import com.example.redis.demo.domain.customer.Customer;
import com.example.redis.demo.repo.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/customers")
public class CustomerRest {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping()
    public ResponseEntity<?> getcustomers() {
        List<Customer> customers = (List<Customer>) this.customerRepo.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getcustomersById(@PathVariable String id) {
        Optional<Customer> customer = this.customerRepo.findById(id);
        if (!customer.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postCustomers(@RequestBody Customer body) {
        Customer customer = this.customerRepo.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

}
