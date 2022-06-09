package com.vladwick.service;

import java.util.Set;

import com.vladwick.models.ResponseDTO;
import com.vladwick.models.ResponseDTO_covid;

public interface ScraperService {
	Set<ResponseDTO> getVehicleByModel(String vehicleModel);
	Set<ResponseDTO> getTpuData();
	Set<ResponseDTO_covid> getCovidInfo();
}
