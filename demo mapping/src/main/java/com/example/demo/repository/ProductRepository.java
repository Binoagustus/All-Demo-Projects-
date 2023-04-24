package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Page<Product> findByAvailability(boolean availability, Pageable pageable);
	
	@Query(value = "SELECT p FROM Product p WHERE p.customer.customerId= :customerId AND p.availability= :availability")
	List<Product> findAllByAvailabilityAndCustomerId(boolean availability, int customerId);
	
	@Query(value = "select * from public.product where customer_id= :customerId", nativeQuery = true)
	List<Product> findAllByCustomerId(int customerId);
}
