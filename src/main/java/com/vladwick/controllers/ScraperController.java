package com.vladwick.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vladwick.models.ResponseDTO;
import com.vladwick.models.ResponseDTO_covid;
import com.vladwick.service.ScraperService;

@RestController
@RequestMapping(path = "/")
public class ScraperController {
	
	@Autowired
    ScraperService scraperService;

    @GetMapping("/car/{vehicleModel}")
    public Set<ResponseDTO> getVehicleByModelPage(@PathVariable String vehicleModel) {
        return scraperService.getVehicleByModel(vehicleModel);
    }
    
    @GetMapping("/tpu")
    public Set<ResponseDTO> getTpu(){
    	return scraperService.getTpuData();
    }
    
    @GetMapping("/covid")
    public Set<ResponseDTO_covid> getCovid() {
    	return scraperService.getCovidInfo();
    }
}
