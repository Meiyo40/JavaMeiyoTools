package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.Url;
import com.meiyotools.main.service.PageService;
import com.meiyotools.main.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/url")
public class UrlController {

    private PageService pageService;
    private UrlService urlService;

    @Autowired
    UrlController(PageService pPageService, UrlService pUrlService) {
        this.pageService = pPageService;
        this.urlService = pUrlService;
    }

    @GetMapping("/{url}")
    public String getExistingUrl(@PathVariable String url)
    {
        String getRealUrl = this.urlService.getRealUrl(url);
        return "redirect:" + getRealUrl;
    }

    @GetMapping
    public String getUrl(HttpServletRequest request, Model model) {
        this.pageService.setUrlShortenerPage(request, model, "");
        return "index";
    }

    @PostMapping
    public String addUrl(@RequestBody String url, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        url = url.split("url=")[1];

        if(url.length() < 10)
            return "index";

        url = java.net.URLDecoder.decode(url);
        String shortUrl = this.urlService.addUrl(url);

        //System.out.println(shortUrl + "  ##  " + url);

        this.pageService.setUrlShortenerPage(request, model, shortUrl);
        return "index";
    }
}
