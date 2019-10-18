package com.example.demo.controller;

import com.example.demo.repository.EhcacheRepository;
import com.example.demo.service.EhcacheService;
import com.example.demo.vo.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
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

    @RequestMapping(value = "/excelDownTest")
    public void excelDownTest () {
        // 새 workbook 생성
        SXSSFWorkbook wb = new SXSSFWorkbook(100);

        // 새 sheet 생성
        Sheet sheet = wb.createSheet("First sheet");

        // 새 row 생성
        Row row = sheet.createRow(0);

        // 새 cell 생성 : title
        for (int i=0; i<3; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(i+" title");
            sheet.setColumnWidth(i, 20*256);
        }

        // 새 cell 생성 : data
        Row dataRow = null;
        List<Customer> list = ehcacheService.findAllByChche();
        int size = list.size();
        for (int i=0; i<size; i++) {
            dataRow = sheet.createRow(i+1);

            dataRow.createCell(0).setCellValue(list.get(i).getId());
            dataRow.createCell(1).setCellValue(list.get(i).getEmail());
            dataRow.createCell(2).setCellValue(list.get(i).getName());

        }

        String excelFileName = "C:/kang_work/testExcel.xlsx";
        try {
            FileOutputStream out = new FileOutputStream(excelFileName);
            wb.write(out);
            out.close();
            wb.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
