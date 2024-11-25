package com.codeWithGanesh.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.codeWithGanesh.model.Orders;
import com.codeWithGanesh.repository.OrdersRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Value("${razorpay.key.id}")
    private String razorpayId;
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;
    @PostConstruct
    public void init() throws RazorpayException {
        this.razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);
    }

    public Orders createOrder(Orders order) throws  RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", order.getAmount() * 100);
        options.put("currency", "INR");
        options.put("receipt", order.getEmail());
        Order razorpayOrder = razorpayClient.orders.create(options);
        if (razorpayOrder != null) {
            order.setRazorpayOrderId(razorpayOrder.get("id"));
            order.setOrderStatus(razorpayOrder.get("status"));

        }
        return  ordersRepository.save(order);
    }

    public Orders updateStatus(Map<String, String> map) {
        String razorpayId = map.get("razorpay_order_id");
        Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
        order.setOrderStatus("Payment Done");
        Orders orders = ordersRepository.save(order);
        return orders;
    }
}
