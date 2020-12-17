package com.example.redis.demo.repo;

import java.util.List;

import com.example.redis.demo.domain.customer.Customer;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface CustomerRepository extends CrudRepository<Customer, String> {

    List<Customer> findByFirstName(String firstName);
}