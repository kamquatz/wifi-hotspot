package com.surfcode.services;
import com.surfcode.models.Device;
import com.surfcode.models.Site;
import com.surfcode.models.Station;
import java.util.List;

public interface IDeviceService{
    boolean add(String phone, Site site, Station station);
    boolean update(Device device, Site site);
    Device find(String phone, String site);
    List<Device> findAll(Site site);
    List<Device> findAllExpired(Site site);
}
