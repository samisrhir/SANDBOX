package com.hps.sandbox.repository;

import com.hps.sandbox.entity.Product;
import com.hps.sandbox.entity.ProductRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findProductByNameIgnoreCase(String name);

}
