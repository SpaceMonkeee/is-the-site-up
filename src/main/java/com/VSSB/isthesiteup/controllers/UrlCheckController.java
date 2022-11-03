package com.VSSB.isthesiteup.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlCheckController {
    private final String SITE_IS_UP = "Site is up!";
    private final String SITE_IS_DOWN = "Site is down!";
    private final String INCORRECT_URL = "URL is incorrect!";

    //usage:
    //localhost:8080/check?url=https://google.com
    
    @GetMapping("/check")
    public String getUrlStatusMessage(@RequestParam String url){
        String returnMessage = "";
        try {
            URL urlObj = new URL(url); //gets url from omnibox
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection(); //opens connection to site
            conn.setRequestMethod("GET"); //sets method of url request to get data
            conn.connect(); //attempts to connect
            int responseCodeValue = conn.getResponseCode() / 100;  // if response code is in 200 - 300 range, site is up
            if (responseCodeValue == 2 || responseCodeValue == 3){
                returnMessage = SITE_IS_UP;
            }
            else{
                returnMessage = SITE_IS_DOWN;
            }
        } catch (MalformedURLException e) { // bad url
            returnMessage = INCORRECT_URL;
        } catch (IOException e) { //IO issue means site is down
            returnMessage = SITE_IS_DOWN;
        }

        return returnMessage;
    }

}
