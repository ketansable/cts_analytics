package com.analytics.service;

import com.analytics.repository.SalesRepository;
import com.analytics.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Map<String, Object> getAnalyticsData() {
        List<Object[]> salesData = salesRepository.findAll().stream()
                .map(sales -> new Object[]{sales.getProductName(), sales.getSalesAmount()})
                .toList();

        Map<String, Integer> ageGroupCount = new HashMap<>();
        customerRepository.findAll().forEach(customer -> 
            ageGroupCount.merge(customer.getAgeGroup(), 1, Integer::sum)
        );

        Map<String, Object> analyticsData = new HashMap<>();
        analyticsData.put("totalSales", salesData.stream().mapToInt(data -> (int) data[1]).sum());
        analyticsData.put("topSellingProducts", salesData);
        analyticsData.put("customerDemographics", ageGroupCount);

        return analyticsData;
    }
}

