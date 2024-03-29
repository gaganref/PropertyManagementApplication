package com.pma.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PmaErrorController implements ErrorController {

    @RequestMapping("/page-not-found")
    public String handleError() {
        return "pageNotFound";
    }
}
