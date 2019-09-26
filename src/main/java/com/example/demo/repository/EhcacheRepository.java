package com.example.demo.repository;

import com.example.demo.vo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EhcacheRepository extends JpaRepository<Customer, Long> {
}
