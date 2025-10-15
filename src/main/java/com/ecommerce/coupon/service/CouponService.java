package com.ecommerce.coupon.service;

import com.ecommerce.coupon.dto.CouponCreateRequest;
import com.ecommerce.coupon.dto.CouponResponse;
import com.ecommerce.coupon.dto.ValidationResponse;
import com.ecommerce.coupon.exception.CouponExpiredException;
import com.ecommerce.coupon.exception.CouponNotFoundException;
import com.ecommerce.coupon.exception.InvalidCouponException;
import com.ecommerce.coupon.model.Coupon;
import com.ecommerce.coupon.model.DiscountType;
import com.ecommerce.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Сервис для работы с купонами
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private static final String CODE_PREFIX = "SALE";
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    /**
     * Генерация уникального кода купона
     *
     * @return уникальный код
     */
    private String generateCode() {
        String code;
        do {
            // Генерируем код формата: SALE + 6 случайных символов (A-Z, 0-9)
            StringBuilder codeBuilder = new StringBuilder(CODE_PREFIX);
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (int i = 0; i < CODE_LENGTH; i++) {
                codeBuilder.append(chars.charAt(random.nextInt(chars.length())));
            }
            code = codeBuilder.toString();
        } while (couponRepository.existsByCode(code));

        return code;
    }

    /**
     * Создать новый купон
     *
     * @param request данные для создания купона
     * @return созданный купон
     */
    @Transactional
    public CouponResponse createCoupon(CouponCreateRequest request) {
        log.info("Создание нового купона с типом скидки: {}", request.getDiscountType());

        Coupon coupon = Coupon.builder()
                .code(generateCode())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .minOrderAmount(request.getMinOrderAmount())
                .expiryDate(request.getExpiryDate())
                .usageLimit(request.getUsageLimit())
                .usageCount(0)
                .active(true)
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);
        log.info("Купон успешно создан с кодом: {}", savedCoupon.getCode());

        return CouponResponse.fromEntity(savedCoupon);
    }

    /**
     * Получить все купоны
     *
     * @return список всех купонов
     */
    @Transactional(readOnly = true)
    public List<CouponResponse> getAllCoupons() {
        log.info("Получение списка всех купонов");
        return couponRepository.findAll().stream()
                .map(CouponResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Получить купон по коду
     *
     * @param code код купона
     * @return купон
     */
    @Transactional(readOnly = true)
    public CouponResponse getCouponByCode(String code) {
        log.info("Поиск купона по коду: {}", code);
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Купон с кодом '" + code + "' не найден"));
        return CouponResponse.fromEntity(coupon);
    }

    /**
     * Валидация и применение купона
     *
     * @param code        код купона
     * @param orderAmount сумма заказа
     * @return результат валидации с расчетом скидки
     */
    @Transactional
    public ValidationResponse validateAndApply(String code, BigDecimal orderAmount) {
        log.info("Валидация купона: {} для заказа на сумму: {}", code, orderAmount);

        // Найти купон
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Купон с кодом '" + code + "' не найден"));

        // Проверка: купон активен
        if (!coupon.getActive()) {
            log.warn("Купон {} неактивен", code);
            throw new InvalidCouponException("Купон неактивен");
        }

        // Проверка: срок действия
        if (LocalDateTime.now().isAfter(coupon.getExpiryDate())) {
            log.warn("Купон {} истек", code);
            throw new CouponExpiredException("Срок действия купона истек");
        }

        // Проверка: лимит использований
        if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
            log.warn("Купон {} превысил лимит использований", code);
            throw new InvalidCouponException("Купон превысил лимит использований");
        }

        // Проверка: минимальная сумма заказа
        if (orderAmount.compareTo(coupon.getMinOrderAmount()) < 0) {
            log.warn("Сумма заказа {} меньше минимальной {}", orderAmount, coupon.getMinOrderAmount());
            throw new InvalidCouponException(
                    String.format("Минимальная сумма заказа для этого купона: %.2f₽", coupon.getMinOrderAmount())
            );
        }

        // Расчет скидки
        BigDecimal discount = calculateDiscount(coupon, orderAmount);
        BigDecimal finalPrice = orderAmount.subtract(discount).max(BigDecimal.ZERO);

        // Увеличить счетчик использований
        coupon.setUsageCount(coupon.getUsageCount() + 1);
        couponRepository.save(coupon);

        log.info("Купон {} успешно применен. Скидка: {}, Итоговая цена: {}", code, discount, finalPrice);

        return ValidationResponse.builder()
                .success(true)
                .message("Купон успешно применен")
                .originalPrice(orderAmount)
                .discount(discount)
                .finalPrice(finalPrice)
                .build();
    }

    /**
     * Расчет скидки в зависимости от типа купона
     *
     * @param coupon      купон
     * @param orderAmount сумма заказа
     * @return размер скидки
     */
    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount) {
        BigDecimal discount;

        switch (coupon.getDiscountType()) {
            case PERCENTAGE:
                // Процентная скидка: (orderAmount * discountValue / 100)
                discount = orderAmount
                        .multiply(coupon.getDiscountValue())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                log.debug("Процентная скидка {}%: {}", coupon.getDiscountValue(), discount);
                break;

            case FIXED_AMOUNT:
                // Фиксированная скидка (не больше суммы заказа)
                discount = coupon.getDiscountValue().min(orderAmount);
                log.debug("Фиксированная скидка: {}", discount);
                break;

            case FREE_SHIPPING:
                // Бесплатная доставка (можно добавить логику расчета стоимости доставки)
                discount = BigDecimal.ZERO;
                log.debug("Бесплатная доставка применена");
                break;

            default:
                discount = BigDecimal.ZERO;
                log.warn("Неизвестный тип скидки: {}", coupon.getDiscountType());
        }

        return discount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Удалить купон по коду
     *
     * @param code код купона
     */
    @Transactional
    public void deleteCoupon(String code) {
        log.info("Удаление купона с кодом: {}", code);
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new CouponNotFoundException("Купон с кодом '" + code + "' не найден"));
        couponRepository.delete(coupon);
        log.info("Купон {} успешно удален", code);
    }
}
