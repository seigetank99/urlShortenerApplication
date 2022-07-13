package com.example.urlshortenerapplication.service;

import com.example.urlshortenerapplication.model.Url;
import com.example.urlshortenerapplication.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    public Url generateShortLink(UrlDto urlDto);
    public Url presistShortLink(Url url);
    public Url getEncodedShortLink(String url);
}
