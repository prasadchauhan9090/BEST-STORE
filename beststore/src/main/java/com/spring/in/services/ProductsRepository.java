package com.spring.in.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.in.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

}
