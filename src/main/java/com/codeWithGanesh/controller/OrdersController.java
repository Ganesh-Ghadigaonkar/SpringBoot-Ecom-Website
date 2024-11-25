package com.codeWithGanesh.controller;

import com.razorpay.RazorpayException;
import com.codeWithGanesh.model.Orders;
import com.codeWithGanesh.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/chcekout")
    public String checkout() {
        return "orders";
    }

    @PostMapping(value = "/createOrder", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) throws RazorpayException {
        Orders razorpayOrder = ordersService.createOrder(orders);
        return new ResponseEntity<>(razorpayOrder, HttpStatus.CREATED);
    }

    @PostMapping("/paymentCallback")
    public String paymentCallBack(@RequestParam Map<String, String> response)  {
        ordersService.updateStatus(response);
        return "success";
    }
}
