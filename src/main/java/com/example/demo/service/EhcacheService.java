package com.example.demo.service;

import com.example.demo.repository.EhcacheRepository;
import com.example.demo.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EhcacheService {
    @Autowired
    private EhcacheRepository ehcacheRepository;

    @Cacheable(value = "LocalCacheData")
    public List<Customer> findAllByChche() {
        return ehcacheRepository.findAll();
    }

    public List<Customer> findAllNonChche() {
        return ehcacheRepository.findAll();
    }

    @CacheEvict(value = "LocalCacheData")
    public void deleteChche() {}
}
