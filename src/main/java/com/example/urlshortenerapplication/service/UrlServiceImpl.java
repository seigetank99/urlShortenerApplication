package com.example.urlshortenerapplication.service;

import antlr.CharQueue;
import com.example.urlshortenerapplication.model.Url;
import com.example.urlshortenerapplication.model.UrlDto;
import com.example.urlshortenerapplication.repository.UrlRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;

@Component
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl()))
        {
            //check encoded url exists
            //if it does then don't call generateShortLink
            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url urlToPersist = new Url();
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortLink(encodedUrl);
            Url urlToReturn = presistShortLink(urlToPersist);

            if(urlToReturn != null)
                return urlToReturn;

            return null;
        }

        return null;
    }

    private String encodeUrl(String url){
        String encodeUrl = "";
        int urlCode = url.hashCode();
        encodeUrl = Hashing.adler32()
                .hashInt(urlCode)
                .toString();
        return encodeUrl;
    }

    @Override
    public Url presistShortLink(Url url) {
        Url urlToReturn = urlRepository.save(url);

        return urlToReturn;
    }

    @Override
    public Url getEncodedShortLink(String url) {
        Url urlToReturn = urlRepository.findByShortLink(url);

        return urlToReturn;
    }

}
