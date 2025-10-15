package com.ecommerce.coupon.controller;

import com.ecommerce.coupon.dto.CouponCreateRequest;
import com.ecommerce.coupon.dto.CouponResponse;
import com.ecommerce.coupon.dto.ValidationRequest;
import com.ecommerce.coupon.dto.ValidationResponse;
import com.ecommerce.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller для работы с купонами
 */
@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    /**
     * Создать новый купон
     * POST /api/coupons
     *
     * @param request данные для создания купона
     * @return созданный купон
     */
    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CouponCreateRequest request) {
        log.info("POST /api/coupons - Создание купона");
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Получить список всех купонов
     * GET /api/coupons
     *
     * @return список купонов
     */
    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        log.info("GET /api/coupons - Получение всех купонов");
        List<CouponResponse> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    /**
     * Получить купон по коду
     * GET /api/coupons/{code}
     *
     * @param code код купона
     * @return купон
     */
    @GetMapping("/{code}")
    public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String code) {
        log.info("GET /api/coupons/{} - Получение купона по коду", code);
        CouponResponse response = couponService.getCouponByCode(code);
        return ResponseEntity.ok(response);
    }

    /**
     * Применить купон (валидация и расчет скидки)
     * POST /api/coupons/{code}/apply
     *
     * @param code    код купона
     * @param request данные для валидации (сумма заказа)
     * @return результат валидации с расчетом
     */
    @PostMapping("/{code}/apply")
    public ResponseEntity<ValidationResponse> applyCoupon(
            @PathVariable String code,
            @Valid @RequestBody ValidationRequest request) {
        log.info("POST /api/coupons/{}/apply - Применение купона для суммы {}", code, request.getOrderAmount());
        ValidationResponse response = couponService.validateAndApply(code, request.getOrderAmount());
        return ResponseEntity.ok(response);
    }

    /**
     * Удалить купон
     * DELETE /api/coupons/{code}
     *
     * @param code код купона
     * @return 204 No Content
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable String code) {
        log.info("DELETE /api/coupons/{} - Удаление купона", code);
        couponService.deleteCoupon(code);
        return ResponseEntity.noContent().build();
    }
}
