package com.fmi.springweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RestController
public class FakePaymentApiController {

    @PostMapping("/api/totallysafepayment")
    public ModelAndView passPayment(HttpServletRequest httpServletRequest) {
        String redirectUrl = "core/funds/complete";
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/" + redirectUrl);
    }
}
