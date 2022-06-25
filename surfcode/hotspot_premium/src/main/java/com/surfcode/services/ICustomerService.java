package com.surfcode.services;
import com.surfcode.models.Customer;

public interface ICustomerService{
    boolean add(String phone, String code, int amount, int days);
    Customer getActive(String phone);
}
