package phoenix.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.dto.ProductOrderRequestDto;
import phoenix.dto.ProductRequestDto;
import phoenix.dto.ProductResponseDto;
import phoenix.service.ProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@Slf4j
@RequestMapping("/api/")
public class ProductController {
	private final ProductService productService;

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/product/add")
	public ProductResponseDto addProducts(@Valid @RequestBody ProductRequestDto productRequestDto) {
		logger.info("Request : "+ productRequestDto.toString());
		ProductResponseDto productResponseDto = productService.save(productRequestDto);
		logger.info("Response : "+ productResponseDto.toString());
		return productResponseDto;
	}


	@PostMapping("/product/update")
	public ProductResponseDto updateProducts(@Valid @RequestBody ProductRequestDto productRequestDto) {
		logger.info("Request : "+productRequestDto.toString());
		ProductResponseDto productResponseDto = productService.update(productRequestDto);
		logger.info("Response : "+productResponseDto.toString());
		return productResponseDto;
	}

	@PostMapping("/product/updateQuantity")
	public ResponseEntity<List<ProductResponseDto>> updateProductsQuantity(
			@Valid @RequestBody ProductOrderRequestDto productOrderRequestDto) {

		logger.info("Request : "+productOrderRequestDto.toString());

		Map<Long, Integer> productRequests = new HashMap<>();
		productOrderRequestDto.getProductRequests().forEach(productInfo -> {
			productRequests.put(productInfo.getProductId(), productInfo.getQty());
		});

		List<ProductResponseDto> productResponseList = productService.updateProductsQuantity(productRequests, "update");
		logger.info("Response : "+productResponseList.toString());
		return new ResponseEntity<>(productResponseList, HttpStatus.OK);
	}

	@PostMapping("/product/revokeQuantity")
	public ResponseEntity<List<ProductResponseDto>> revertProductsQuantity(
			@Valid @RequestBody ProductOrderRequestDto productOrderRequestDto) {

		logger.info("Request : "+productOrderRequestDto.toString());
		Map<Long, Integer> productRequests = new HashMap<>();
		productOrderRequestDto.getProductRequests().forEach(productInfo -> {
			productRequests.put(productInfo.getProductId(), productInfo.getQty());
		});
		List<ProductResponseDto> productResponseList =productService.updateProductsQuantity(productRequests, "revoke");
		logger.info("Response : "+productResponseList.toString());
		return new ResponseEntity<>(productResponseList, HttpStatus.OK);
	}

	@GetMapping("/product/list")
	public List<ProductResponseDto> getAllProducts() {
		List<ProductResponseDto> productResponseDtoList = productService.getAllProducts();
		logger.info("Response : "+ productResponseDtoList.toString());
		return productResponseDtoList;
	}

	@DeleteMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable(value = "id") Long id) {
		productService.delete(id);
		return "success";
	}

}
