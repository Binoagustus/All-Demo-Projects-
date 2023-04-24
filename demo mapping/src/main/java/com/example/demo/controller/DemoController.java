package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response;
import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.service.IDemoService;
import com.example.demo.wrapperObjects.ProductList;

@RestController
@RequestMapping("/mapping-demo")
public class DemoController {

	@Autowired
	IDemoService service;
	
	@PostMapping("/addCustomer")
	public ResponseEntity<Response> addCustomers(@RequestBody Customer customer) {
		Customer person = service.addCustomer(customer);
		Response response = new Response("customer added sucessfully", person);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/addProduct")
	public ResponseEntity<Response> addProducts(@RequestBody Product product) {
		service.addProduct(product);
		Response response = new Response("product added sucessfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProductById/{productId}")
	public ResponseEntity<Response> deleteProduct(@PathVariable int productId) {
		service.deleteProduct(productId);
		Response response = new Response("product deleted sucessfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewAllCustomers")
	public ResponseEntity<Response> viewAllCustomers() {
		List<Customer> customers = service.viewAllCustomers();
		Response response = new Response("List of customers", customers);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<Response> bulkProducts(@RequestBody ProductList products) {
		service.addBulkProducts(products);
		Response response = new Response("bulk products added succesfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewProducts")
	public ResponseEntity<Response> viewAllProducts() {
		List<Product> products = service.viewProducts();
		Response response = new Response("These are the list of products", products);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewPagesOfProducts/{pageNumber}/{pageSize}")
	public ResponseEntity<Response> viewPagesOfProducts(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Page<Product> productPages = service.viewPagesOfProducts(pageNumber, pageSize);
		Map<String, Object> pages = new HashMap<>();
		pages.put("pages of products", productPages.getContent());
		pages.put("Current page", productPages.getNumber());
		pages.put("TotalItems", productPages.getTotalElements());
		pages.put("TotalPages", productPages.getTotalPages());
		Response response = new Response("Pages of products are ", pages);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewSortAndFilterPages/{availability}/{pageNumber}/{pageSize}")
	public ResponseEntity<Response> sortingAndFiltering(@PathVariable boolean availability, @PathVariable int pageNumber, @PathVariable int pageSize) {
		Page<Product> productPages = service.viewSortAndFilterPages(availability, pageNumber, pageSize);
		Map<String, Object> pages = new HashMap<>();
		pages.put("pages of products", productPages.getContent());
		pages.put("Current page", productPages.getNumber());
		pages.put("TotalItems", productPages.getTotalElements());
		pages.put("TotalPages", productPages.getTotalPages());
		Response response = new Response("Pages of products after filter and sorting are ", pages);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/purchase")
	public ResponseEntity<Response> purchaseProducts(@RequestBody Product order) {
		service.purchaseProducts(order);
		Response response = new Response("product has been purchased");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getproducts/{consumerId}")
	public ResponseEntity<Response> getProductsByConsumerId(@PathVariable int consumerId) {
		List<Product> products = service.getProductsByConsumerId(consumerId);
		Response response = new Response("these are the products", products);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getProductJPQL/{customerId}/{availability}")
	public ResponseEntity<Response> getProductByConsumerIdAndAvailability(@PathVariable int customerId, @PathVariable boolean availability) {
		List<Product> products = service.getProductByConsumerIdAndAvailability(customerId, availability);
		Response response = new Response("products by consumerId and availability", products);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
