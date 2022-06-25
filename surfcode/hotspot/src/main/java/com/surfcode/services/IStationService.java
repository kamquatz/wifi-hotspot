package com.surfcode.services;
import com.surfcode.models.Device;
import com.surfcode.models.Site;
import com.surfcode.models.Station;
import java.util.List;

public interface IStationService{
    boolean update(Station station, Site site,int lastUser);
    Station find(String name, String site);
    List<Station> findAll(Site site);
}
