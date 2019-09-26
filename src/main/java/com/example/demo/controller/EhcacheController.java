package com.example.demo.controller;

import com.example.demo.repository.EhcacheRepository;
import com.example.demo.service.EhcacheService;
import com.example.demo.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableCaching
public class EhcacheController {
    @Autowired
    EhcacheRepository ehcacheRepository;

    @Autowired
    EhcacheService ehcacheService;

    @RequestMapping(value = "/")
    @ResponseBody
    public String rootIndex () {
        return "rootIndex..";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String saveCustomer () {
        for (int i=1; i<=3000; i++) {
            Customer customer = new Customer();
            customer.setName("kang"+i);
            customer.setEmail(i+"@naver.com");

            ehcacheRepository.save(customer);
        }
        return "rootIndex..";
    }

    @RequestMapping(value = "/findByChche")
    @ResponseBody
    public String findCustomerByChche () {
        long startTime = System.currentTimeMillis();
        List<Customer> list = ehcacheService.findAllByChche();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        //333 > 306 > 356
        System.out.println("list :: "+list.size()+" ::: "+time);
        return "rootIndex..";
    }

    @RequestMapping(value = "/findNonChche")
    @ResponseBody
    public String findCustomerNonChche () {
        long startTime = System.currentTimeMillis();
        List<Customer> list = ehcacheService.findAllNonChche();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        //329 > 409 > 331
        System.out.println("list :: "+list.size()+" ::: "+time);
        return "rootIndex..";
    }

    @RequestMapping(value = "/deleteChche")
    @ResponseBody
    public String deleteChche () {
        ehcacheService.deleteChche();
        return "rootIndex..";
    }

}
