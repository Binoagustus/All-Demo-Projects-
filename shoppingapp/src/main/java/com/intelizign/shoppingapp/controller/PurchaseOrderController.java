package com.intelizign.shoppingapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intelizign.shoppingapp.model.Product;
import com.intelizign.shoppingapp.model.PurchaseOrder;
import com.intelizign.shoppingapp.model.User;
import com.intelizign.shoppingapp.response.HigherSoldProductsResponse;
import com.intelizign.shoppingapp.response.ResponseHandler;
import com.intelizign.shoppingapp.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/porder")
public class PurchaseOrderController {

	Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	PurchaseOrderService poService;
	
	@PostMapping("/purchase")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> purchase(@RequestParam String productName, HttpServletRequest request) {
		try {
			PurchaseOrder purchaseOrder = poService.purchaseProduct(productName, request.getUserPrincipal().getName());
			return ResponseHandler.generateResponse("Product purchased succesfully", true, HttpStatus.OK, purchaseOrder);
		} catch(Exception ex) {
			logger.error("Purchase Failed " + ex.getMessage());
			ex.printStackTrace();
			return ResponseHandler.generateResponse("Product was not added", false, HttpStatus.OK, null);
		}
	}
	
	@GetMapping("/viewallorders")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> viewAllOrders() {
		try {
			poService.viewAllOrders();
			return ResponseHandler.generateResponse("These are the list of orders placed", true, HttpStatus.OK, null);
		} catch(Exception ex) {
			logger.error("Could not retrieve the list of purchases" + ex.getMessage());
			return ResponseHandler.generateResponse("The orders could not be retrieved", false, HttpStatus.OK, null);
		}
	}
	
	@GetMapping("/view_products_by_username")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> viewUserOrderProducts(HttpServletRequest request) {
		try {
			List<Product> products= poService.viewProducts(request);
			return ResponseHandler.generateResponse("These are the list of products of user", true, HttpStatus.OK, products);
		} catch(Exception ex) {
			logger.error("Could not retrieve the list of purchases" + ex.getMessage());
			return ResponseHandler.generateResponse("The List of Products could not be ", false, HttpStatus.OK, null);
		}
	}
	
	@GetMapping("/view_users_by_productname")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> viewUsersByProductName(@RequestParam String productName) {
		try {
			List<User> users= poService.viewUserByProductName(productName);
			return ResponseHandler.generateResponse("List of users retrieved succesfully", true, HttpStatus.OK, users);
		} catch(Exception ex) {
			logger.error("Could not retrieve the list of purchases" + ex.getMessage());
			return ResponseHandler.generateResponse("The List of Users could not be retrieved", false, HttpStatus.OK, null);
		}
	}

	@GetMapping("/view_products_with_sales")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getListOfProductsWithHighSales(@RequestParam int month) {
		try {
			List<HigherSoldProductsResponse> highSales = poService.viewListOfProductsWithHighSales(month);
			return ResponseHandler.generateResponse("List of products with sales is", true, HttpStatus.OK, highSales);
		} catch(Exception ex) {
			logger.error("Could not retrieve the list of products with high sales" + ex.getMessage());
			return ResponseHandler.generateResponse("Could not retrieve the list of products with high sales", false, HttpStatus.OK, null);
		}
	}
}
