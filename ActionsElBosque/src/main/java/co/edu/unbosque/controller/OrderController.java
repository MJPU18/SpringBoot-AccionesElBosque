package co.edu.unbosque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.request.OrderRequest;
import co.edu.unbosque.service.OrderService;
import co.edu.unbosque.service.UserActivityService;
import co.edu.unbosque.service.UserService;
import co.edu.unbosque.util.EmailSender;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alpaca/orders")
@CrossOrigin(origins = { "http://localhost:8085", "http://localhost:4200", "*"  })
public class OrderController {

	private final OrderService orderService;
	@Autowired
	private UserService userServ;
	@Autowired
	private UserActivityService userActServ;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/{accountId}")
	public Mono<String> placeOrder(@PathVariable String accountId, @RequestBody OrderRequest orderRequest) {
		Mono<String> order = orderService.placeOrder(accountId, orderRequest);
		
		User user = userServ.getUserByAlpacaId(accountId);

		String symbol = orderRequest.getSymbol();
		String quantity = orderRequest.getQty();
		String type = orderRequest.getType();
		String details;

		if ("buy".equalsIgnoreCase(orderRequest.getSide())) {
			details = "Se realizo una orden de compra de tipo "+type+", el usuario " + user.getEmail() + " compro " + quantity
					+  " acciones de "  + symbol + ".";
			String html = EmailSender.buildOrderConfirmationEmail(user.getFirstName()+" "+user.getLastName(), orderRequest.getSide(), type, symbol, quantity);
			EmailSender.sendEmail(user.getEmail(), "Confirmación de compra en Acciones ElBosque", html);
			userActServ.logUserActivity(user.getUserId(), "BUY_ORDER", details);
		} else if ("sell".equalsIgnoreCase(orderRequest.getSide())) {
			details = "Se realizo una orden de venta de tipo "+type+", el usuario " + user.getEmail() + " vendio " + quantity
					+ " acciones de " + symbol + ".";
			String html = EmailSender.buildOrderConfirmationEmail(user.getFirstName()+" "+user.getLastName(), orderRequest.getSide(), type, symbol, quantity);
			EmailSender.sendEmail(user.getEmail(), "Confirmación de venta en Acciones ElBosque", html);
			userActServ.logUserActivity(user.getUserId(), "SELL_ORDER", details);
		}
		return order;
	}
}