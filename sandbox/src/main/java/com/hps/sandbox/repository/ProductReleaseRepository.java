package com.hps.sandbox.repository;

import com.hps.sandbox.entity.ProductRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductReleaseRepository extends JpaRepository<ProductRelease,Integer> {
    @Query("SELECT pr FROM ProductRelease pr WHERE pr.product.id = :productId")
    List<ProductRelease> findByProductId(Integer productId);
}
