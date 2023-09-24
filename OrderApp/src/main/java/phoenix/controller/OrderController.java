package phoenix.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phoenix.dto.OrderRequestDto;
import phoenix.dto.OrderResponseDto;
import phoenix.dto.ProductResponseDto;
import phoenix.service.OrderService;

import javax.validation.Valid;
import java.util.List;

/**
 * User Khan, C M Abdullah
 * Ref:
 */
@RestController
@Slf4j
@RequestMapping("/api/")
public class OrderController {

	private final OrderService orderService;

	private Logger logger = LoggerFactory.getLogger(OrderController.class);


	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order/submit")
	public OrderResponseDto addProducts(@Valid @RequestBody OrderRequestDto orderRequestDto) {
		String userEmail = "abdullah21@gmail.com";
		logger.info("Request : "+ orderRequestDto.toString());
		OrderResponseDto orderResponseDto = orderService.save(userEmail, orderRequestDto);
		logger.info("Response : "+ orderResponseDto.toString());
		return orderResponseDto;
	}

	@GetMapping("/order/list")
	public List<OrderResponseDto> getAllOrder() {
		List<OrderResponseDto> orderResponseDtoList = orderService.getOrderList();
		logger.info("Response : "+ orderResponseDtoList.toString());
		return orderResponseDtoList;
	}
}
