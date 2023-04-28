package com.intelizign.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intelizign.shoppingapp.model.Product;
import com.intelizign.shoppingapp.model.PurchaseOrder;
import com.intelizign.shoppingapp.model.User;
import com.intelizign.shoppingapp.response.HigherSoldProductsResponse;
import com.intelizign.shoppingapp.response.SoldCategoryResponse;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

	@Query(value = "SELECT order.product from PurchaseOrder order WHERE order.user.id = ?1")
	List<Product> findProductsByUserId(Long userId);

	@Query(value = "SELECT order.user from PurchaseOrder order WHERE order.product.id = ?1")
	List<User> findUsersByProductId(Long productId);

	@Query(value = "SELECT new com.intelizign.shoppingapp.response.HigherSoldProductsResponse(o.product,COUNT(o.product)) "
			+ "FROM PurchaseOrder o where DATE_PART('MONTH', datetime) = ?1 GROUP BY o.product ORDER BY COUNT(o.product) DESC ")
	List<HigherSoldProductsResponse> findProductsByHighSalesByMonth(int month);
	
	
//	List<SoldCategoryResponse> findProductsSoldBasedOnCategory();
}
