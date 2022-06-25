package com.surfcode.services;
import com.surfcode.models.Site;
import java.util.List;

public interface ISiteService{
    Site find(String site);
    List<Site> findActive();
}
