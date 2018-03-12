package com.nestos.dvdexchanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    public static final String USER_PAGE_PATH = "/user";

    @RequestMapping(USER_PAGE_PATH)
    String users() {
        return "user";
    }
}
