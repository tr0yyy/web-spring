package com.fmi.springweb.controller;

import com.fmi.springweb.dto.OrderDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.OrderFailedException;
import com.fmi.springweb.service.OrderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/core/funds/add")
    public ModelAndView addFunds(@RequestBody OrderDto orderDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("Beginning payment for " + orderDto.username);
        String redirectUrl = "api/totallysafepayment";
        httpServletResponse.addCookie(new Cookie("X-WebSpring-Payment", Base64.encodeBase64String((orderDto.username + "|" + orderDto.funds).getBytes(StandardCharsets.UTF_8))));
        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/" + redirectUrl);
    }

    @PostMapping("/core/funds/complete")
    public ResponseEntity<ResponseDto> completeFunds(HttpServletRequest httpServletRequest) {
        logger.info("Completing payment...");
        Optional<Cookie> webSpringPayment = Arrays.stream(httpServletRequest.getCookies()).filter(cookie -> cookie.getName().equals("X-WebSpring-Payment")).findFirst();
        if (webSpringPayment.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, null));
        }
        try {
            orderService.completeOrder(webSpringPayment.get().getValue());
            return ResponseEntity.ok(new ResponseDto(true, "Funds added successfully"));
        } catch (OrderFailedException orderFailedException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(502)).body(new ResponseDto(false, orderFailedException.getMessage()));
        }
    }
}
