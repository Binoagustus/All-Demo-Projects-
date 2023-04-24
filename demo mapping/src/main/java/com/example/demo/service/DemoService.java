package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DemoException;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.wrapperObjects.ProductList;

@Service
public class DemoService implements IDemoService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	ProductRepository productRepo;

	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public void addProduct(Product product) {
		productRepo.save(product);
	}

	@Override
	public void addBulkProducts(ProductList products) {
		productRepo.saveAll(products.getProducts());
	}

	@Override
	public List<Product> viewProducts() {
		return productRepo.findAll();
	}

	@Override
	public Page<Product> viewPagesOfProducts(int pageNumber, int pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		return productRepo.findAll(paging);
	}

	@Override
	public Page<Product> viewSortAndFilterPages(boolean availability, int pageNumber, int pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("productPrice").descending());
		return productRepo.findByAvailability(availability, paging);
	}

	@Override
	public void deleteProduct(int productId) {
		productRepo.deleteById(productId);
	}

//	@Override
//	public void purchaseProducts(String customerName, ProductIdList idList) {
//		CustomerProduct ordered = new CustomerProduct();
//		ordered.setCustomer(customerRepo.findByCustomerName(customerName).get());
//		List<Product> productList = idList.getProductId().stream().map(id -> productRepo.findById(id).get()).collect(Collectors.toList());
//		ordered.setProducts(productList);
//		return orderRepo.save(ordered);

//	}

	@Override
	public Product purchaseProducts(Product order) {
		return productRepo.save(order);
	}

	@Override
	public List<Customer> viewAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public void purchaseThroughChild(Customer order) {
		customerRepo.save(order);
	}

	@Override
	public List<Product> getProductsByConsumerId(int consumerId) {
		return productRepo.findAllByCustomerId(consumerId);
	}

	@Override
	public List<Product> getProductByConsumerIdAndAvailability(int customerId, boolean availability) {
		return productRepo.findAllByAvailabilityAndCustomerId(availability, customerId);
	}
}
