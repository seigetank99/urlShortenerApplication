package com.example.urlshortenerapplication.repository;

import com.example.urlshortenerapplication.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortLink(String shortLink);
    public Url findByOriginalUrl(String Url);
}
