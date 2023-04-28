package com.intelizign.shoppingapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.intelizign.shoppingapp.exception.ShoppingException;
import com.intelizign.shoppingapp.model.Category;
import com.intelizign.shoppingapp.model.Product;
import com.intelizign.shoppingapp.repository.CategoryRepository;
import com.intelizign.shoppingapp.repository.ProductRepository;
import com.intelizign.shoppingapp.request.ProductRequest;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	CategoryRepository categoryRepo;

	@Autowired
	FileStorageService fileService;

	public Product addProduct(ProductRequest productRequest) throws IOException {

		Product product = null;

		if (categoryRepo.findByCategoryName(productRequest.getCategoryName()).isPresent()) {
			Optional<Category> category = categoryRepo.findByCategoryName(productRequest.getCategoryName());
			fileService.save(productRequest.getFile());
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(productRequest.getProductName()).toUriString();

			product = new Product(productRequest.getProductName(), productRequest.getPrice(), fileDownloadUri, categoryRepo.findByCategoryName(productRequest.getCategoryName()).get());
			boolean isProductExist = category.get().getProducts().stream()
					.anyMatch(prod -> prod.getProductName().equals(productRequest.getProductName()));

			if (isProductExist) {
				throw new ShoppingException("Product already present in the category");
			} else {
				List<Product> productList = category.get().getProducts();
				productList.add(product);
				category.get().setProducts(productList);
				categoryRepo.save(category.get());
			}

		}
		return product;

	}
//		if (productRepo.findByProductName(productRequest.getProductName()).isPresent()) {
//			throw new ShoppingException("Product already exists try updating the product details");
//		} else {
//			fileService.save(productRequest.getFile());
//			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
//					.path(productRequest.getProductName()).toUriString();
//
//			Product product = new Product(productRequest.getProductName(), productRequest.getPrice(), fileDownloadUri);
//			return productRepo.save(product);
//		}

//	@Transactional
//	public Product updateProduct(Product product) {
//
//		if (productRepo.findByProductName(product.getProductName()).isPresent()) {
//			
//			return productRepo.save(product);
//		} else
//			throw new ShoppingException("Check the product name");
//	}

	public List<Product> getAllProductList() {
		return productRepo.findAll();
	}

	public Product getProduct(String productName) {

		if (productRepo.findByProductName(productName).isPresent()) {
			return productRepo.findByProductName(productName).get();
		} else
			throw new ShoppingException("Check the name you have entered");
	}

	public void addCategory(Category category) {
		if(categoryRepo.findByCategoryName(category.getCategoryName()).isPresent()) {
			throw new ShoppingException(category + "already present");
		} else
			categoryRepo.save(category);
	}
}
