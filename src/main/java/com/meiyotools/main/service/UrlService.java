package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Url;
import com.meiyotools.main.model.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static java.util.Objects.isNull;

@Service
public class UrlService {
    public final UrlRepository urlRepository;

    @Autowired
    UrlService(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }

    public String addUrl(String originalUrl)
    {
        String url = this.getUrl(originalUrl);
        if (isNull(url))
        {
            Url newUrl = new Url();
            String shortUrl = "";
            boolean urlExist = false;
            do {
                shortUrl = this.generateIdentifier();
                urlExist = isNull(this.getRealUrl(shortUrl));
            } while (!urlExist);

            newUrl.setOriginalUrl(originalUrl);
            newUrl.setShortUrl(shortUrl);
            this.urlRepository.save(newUrl);

            return newUrl.getShortUrl();
        }
        return url;
    }

    public String getUrl(String originalUrl)
    {
        Optional<Url> url = this.urlRepository.findByOriginalUrl(originalUrl);

        if(url.isPresent())
            return url.get().getShortUrl();

        return null;
    }

    public String getRealUrl(String shortUrl)
    {
        Optional<Url> url = this.urlRepository.findByShortUrl(shortUrl);

        if(url.isPresent())
            return url.get().getOriginalUrl();

        return null;
    }

    private String generateIdentifier()
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
