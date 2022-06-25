package com.surfcode.services;
import com.surfcode.models.Customer;
import com.surfcode.models.Station;

public interface ILoginService{
    int add(Customer customer, Station station);
}
