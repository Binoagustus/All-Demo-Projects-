package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.wrapperObjects.ProductList;

public interface IDemoService {

	Customer addCustomer(Customer customer);

	void addProduct(Product product);

	void addBulkProducts(ProductList products);

	List<Product> viewProducts();

	Page<Product> viewPagesOfProducts(int pageNumber, int pageSize);

	Page<Product> viewSortAndFilterPages(boolean availability, int pageNumber, int pageSize);

	void deleteProduct(int productId);

	Product purchaseProducts(Product order);

	List<Customer> viewAllCustomers();

	void purchaseThroughChild(Customer order);

	List<Product> getProductsByConsumerId(int consumerId);

	List<Product> getProductByConsumerIdAndAvailability(int customerId, boolean availability);
}
