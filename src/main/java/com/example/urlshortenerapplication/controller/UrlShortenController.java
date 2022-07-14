package com.example.urlshortenerapplication.controller;

import com.example.urlshortenerapplication.model.Url;
import com.example.urlshortenerapplication.model.UrlDto;
import com.example.urlshortenerapplication.model.UrlErrorResponseDto;
import com.example.urlshortenerapplication.model.UrlResponseDto;
import com.example.urlshortenerapplication.service.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UrlShortenController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto){
        Url urlToReturn = urlService.generateShortLink(urlDto);

        if(urlToReturn != null){
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setOriginalUrl(urlToReturn.getOriginalUrl());
            urlResponseDto.setShortLink(urlToReturn.getShortLink());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }

        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("There's an error processing your request. Please try again ");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getOriginalUrl/{shortLink}")
    public String getOriginalUrl(@PathVariable String shortLink){
        Url urlToReturn = urlService.getEncodedShortLink(shortLink);
        return urlToReturn.getOriginalUrl();
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(shortLink)){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Invalid Url");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        Url urlToReturn = urlService.getEncodedShortLink(shortLink);

        if(urlToReturn == null){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url doesn't exist");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        response.sendRedirect(urlToReturn.getOriginalUrl());
        return null;
    }

}


