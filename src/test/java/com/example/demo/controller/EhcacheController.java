package com.example.demo.controller;

import com.example.demo.repository.EhcacheRepository;
import com.example.demo.service.EhcacheService;
import com.example.demo.vo.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EhcacheController {
    @Autowired
    EhcacheRepository ehcacheRepository;

    @Autowired
    EhcacheService ehcacheService;

    @Test
    public void saveCustomer () {
        for (int i=1; i<=3000; i++) {
            Customer customer = new Customer();
            customer.setName("kang"+i);
            customer.setEmail(i+"@naver.com");

            ehcacheRepository.save(customer);
        }
    }

    @Test
    public void findCustomerByChche () {
        long startTime = System.currentTimeMillis();
        List<Customer> list = ehcacheService.findAllByChche();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        //333 > 306 > 356
        System.out.println("list :: "+list.size()+" ::: "+time);
    }

    @Test
    public void findCustomerNonChche () {
        long startTime = System.currentTimeMillis();
        List<Customer> list = ehcacheService.findAllNonChche();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        //329 > 409 > 331
        System.out.println("list :: "+list.size()+" ::: "+time);
    }
}
