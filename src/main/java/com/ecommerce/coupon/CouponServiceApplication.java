package com.ecommerce.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения E-Commerce Coupon Service
 *
 * REST API сервис для генерации и управления купонами и промоакциями
 *
 * @author Mark
 * @version 1.0
 */
@SpringBootApplication
public class CouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("🚀 Coupon Service успешно запущен!");
        System.out.println("📍 URL: http://localhost:8080");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("🌐 Frontend: http://localhost:8080/index.html");
        System.out.println("===========================================\n");
    }
}
