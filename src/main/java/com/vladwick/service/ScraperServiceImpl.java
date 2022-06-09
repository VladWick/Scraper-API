package com.vladwick.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vladwick.models.ResponseDTO;
import com.vladwick.models.ResponseDTO_covid;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;
    
    @Override
    public Set<ResponseDTO> getVehicleByModel(String vehicleModel) {

        Set<ResponseDTO> responseDTOS = new HashSet<>();

        for (String url: urls) {
        	
        	if(url.contains("riyasewana")) {
        		extractDataFromRiyasewana(responseDTOS, url + vehicleModel);
        	}
        }

        return responseDTOS;
    }

    private void extractDataFromRiyasewana(Set<ResponseDTO> responseDTOS, String url) {

        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("content");

            Elements elements = element.getElementsByTag("a");

            for (Element ads: elements) {
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(ads.attr("title")) ) {
                    responseDTO.setTitle(ads.attr("title"));
                    responseDTO.setUrl(ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /* --- --- */
    
    public Set<ResponseDTO> getTpuData() {
    	Set<ResponseDTO> responseDTOS = new HashSet<>();

        for (String url: urls) {
        	
        	if(url.contains("tpu")) {
        		extractDataFromTpu(responseDTOS, url);
        	}
        }
        return responseDTOS;
    }
    
    public void extractDataFromTpu(Set<ResponseDTO> responseDTOS, String url) {
    	try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("main-page");
            Elements elements = element.getElementsByTag("a");
            
            for (Element ads: elements) {
            	Elements h3_in_a = ads.getElementsByTag("h3");
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(ads.attr("href")) ) {
                    responseDTO.setTitle(h3_in_a.text());
                    responseDTO.setUrl(ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /* --- --- */
    
    public Set<ResponseDTO_covid> getCovidInfo() {
    	Set<ResponseDTO_covid> responseDTOS = new HashSet<>();

        for (String url: urls) {
        	
        	if(url.contains("wiki")) {
        		extractDataFromWiki(responseDTOS, url);
        	}
        }
        return responseDTOS;
    }
    
    public void extractDataFromWiki(Set<ResponseDTO_covid> responseDTOS, String url) {
    	try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("covid-19-cases-deaths-and-rates-by-location");
            Elements elements = element.getElementsByTag("tr");

            for (Element ads: elements) {
            	Elements td_in_tr = ads.getElementsByTag("td");

            	String str = td_in_tr.text();
            	if(str.length() != 0) {
            		
            		ArrayList<String> covidNumbers = new ArrayList<>();
                	int k = 0;
                	for(int i = 0 ; i < str.length(); ++i) {
                		String prevNumber = "";
                		if(str.charAt(i) == ' ') {
                			prevNumber = str.substring(k, i);
                			k = i+1;
                			covidNumbers.add(prevNumber);
                		}
                	}
                	covidNumbers.add(str.substring(k, str.length()));
                	
                	ResponseDTO_covid responseDTO = new ResponseDTO_covid();
                	Elements a_in_tr = ads.getElementsByTag("a");
                	
                	responseDTO.setCoutry(a_in_tr.text());
                	responseDTO.setCases(covidNumbers.get(0));
                	responseDTO.setDeaths(covidNumbers.get(1));
                	responseDTO.setRecoveries(covidNumbers.get(2));
          
                	responseDTOS.add(responseDTO);
            	}
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}