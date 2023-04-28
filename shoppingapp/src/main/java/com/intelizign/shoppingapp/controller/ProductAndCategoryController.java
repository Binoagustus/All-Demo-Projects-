package com.intelizign.shoppingapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intelizign.shoppingapp.model.Category;
import com.intelizign.shoppingapp.model.Product;
import com.intelizign.shoppingapp.request.ProductRequest;
import com.intelizign.shoppingapp.response.ResponseHandler;
import com.intelizign.shoppingapp.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductAndCategoryController {

	Logger logger = LoggerFactory.getLogger(ProductAndCategoryController.class);
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/addcategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addCategory(@RequestBody Category category){
		try {
			productService.addCategory(category);
			return ResponseHandler.generateResponse("category has been added", true, HttpStatus.OK, null);
		} catch(Exception ex) {
			logger.error("Category not added");
			return ResponseHandler.generateResponse("category has not been added", false, HttpStatus.OK, null);
		}
	}
	
	@PostMapping("/addproduct")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addProducts(@ModelAttribute ProductRequest product) {
		try {
			Product productResponse = productService.addProduct(product);
			return ResponseHandler.generateResponse("Product added succesfully", true, HttpStatus.OK, productResponse);
		} catch(Exception ex) {
			logger.error("Could not add the product " + ex.getMessage());
			ex.printStackTrace();
			return ResponseHandler.generateResponse("Product was not added", false, HttpStatus.OK, null);
		}
	}

	
	@GetMapping("/viewallproducts")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> viewAllProducts() {
		try {
			List<Product> productList = productService.getAllProductList();
			return ResponseHandler.generateResponse("These are the list of products", true, HttpStatus.OK, productList);
		} catch(Exception ex) {
			logger.error("No product available" + ex.getMessage());
			return ResponseHandler.generateResponse("No products available to display", false, HttpStatus.OK, null);
		}
	}
	
	@GetMapping("/viewProduct")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> viewProduct(@RequestParam String productName) {
		try {
			Product product = productService.getProduct(productName);
			return ResponseHandler.generateResponse("Product retrieved succesfully", true, HttpStatus.OK, product);
		} catch(Exception ex) {
			logger.error("No product available" + ex.getMessage());
			return ResponseHandler.generateResponse("No products available to display", false, HttpStatus.OK, null);
		}
	}
}
