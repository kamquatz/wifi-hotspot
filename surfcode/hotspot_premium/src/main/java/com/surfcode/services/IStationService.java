package com.surfcode.services;
import com.surfcode.models.Station;

public interface IStationService{
    boolean update(Station station, int lastUser);
    Station find(String name, String town);
}
