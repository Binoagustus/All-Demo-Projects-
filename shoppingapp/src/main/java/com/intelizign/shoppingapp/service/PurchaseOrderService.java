package com.intelizign.shoppingapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intelizign.shoppingapp.exception.ShoppingException;
import com.intelizign.shoppingapp.model.Product;
import com.intelizign.shoppingapp.model.PurchaseOrder;
import com.intelizign.shoppingapp.model.User;
import com.intelizign.shoppingapp.repository.ProductRepository;
import com.intelizign.shoppingapp.repository.PurchaseOrderRepository;
import com.intelizign.shoppingapp.repository.UserRepository;
import com.intelizign.shoppingapp.response.HigherSoldProductsResponse;

@Service
public class PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository poRepo;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	UserRepository userRepo;

	/**
	 * Returns Purchase Order with User Object and Product Object
	 * 
	 * @param productName - Name of the product
	 * @param name        - Name of the User
	 * @return - Purchase Order
	 */
	public PurchaseOrder purchaseProduct(String productName, String name) {
		Optional<User> user = userRepo.findByUsername(name);
		if (user.isPresent()) {

			Optional<Product> product = productRepo.findByProductName(productName);
			if (product.isPresent()) {
				LocalDateTime dateTime = LocalDateTime.now();
				PurchaseOrder po = new PurchaseOrder(user.get(), product.get(), dateTime);
				return poRepo.save(po);

			} else
				throw new ShoppingException("product doesn't exists");

		} else
			throw new ShoppingException("user not exists");
	}

	/**
	 * Returns list of all Purchase Orders
	 */
	public List<PurchaseOrder> viewAllOrders() {
		return poRepo.findAll();
	}

	/**
	 * Returns the list of products based on the User Id
	 * 
	 * @param request - HTTP Servlet Request
	 * @return - List of Products
	 */
	public List<Product> viewProducts(HttpServletRequest request) {

		String username = request.getUserPrincipal().getName();
		Optional<User> user = userRepo.findByUsername(username);
		return poRepo.findProductsByUserId(user.get().getUserId());
	}

	public List<User> viewUserByProductName(String productName) {

		Optional<Product> product = productRepo.findByProductName(productName);
		return poRepo.findUsersByProductId(product.get().getProductId());
	}

	public List<HigherSoldProductsResponse> viewListOfProductsWithHighSales(int month) {
		return poRepo.findProductsByHighSalesByMonth(month);
	}
}
