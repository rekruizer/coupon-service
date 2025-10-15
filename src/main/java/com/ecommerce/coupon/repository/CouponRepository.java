package com.ecommerce.coupon.repository;

import com.ecommerce.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository для работы с купонами
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * Найти купон по коду
     *
     * @param code код купона
     * @return Optional с купоном
     */
    Optional<Coupon> findByCode(String code);

    /**
     * Проверить существование купона по коду
     *
     * @param code код купона
     * @return true если существует
     */
    boolean existsByCode(String code);
}
