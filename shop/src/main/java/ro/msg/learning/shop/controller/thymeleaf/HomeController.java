package ro.msg.learning.shop.controller.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.msg.learning.shop.controller.CustomerController;
import ro.msg.learning.shop.controller.OrderController;
import ro.msg.learning.shop.controller.ProductController;
import ro.msg.learning.shop.dto.AddressDto;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.thymeleaf.ShoppingCartDto;
import ro.msg.learning.shop.service.thymeleaf.ShoppingCartService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final OrderController orderController;
    private final ProductController productController;
    private final ShoppingCartService shoppingCartService;
    private final CustomerController customerController;

    @GetMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }

    @GetMapping(value = "/home")
    public String getAllProducts(Model model) {
        var products = productController.getAllProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping(value = "/product")
    public String getProduct(@RequestParam(name = "id") String id, Model model) {
        var product = productController.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("orderDetailDto", OrderDetailDto.builder().productId(product.getId()).productName(product.getName()).quantity(1).build());

        return "product";
    }

    @PostMapping(value = "/shoppingCart")
    public String addToShoppingCart(OrderDetailDto orderDetailDto, RedirectAttributes redirectAttributes) {
        if (shoppingCartService.addToShoppingCart(orderDetailDto) != null) {
            redirectAttributes.addFlashAttribute("message", "Product added successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Product can't be added to cart!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/home";
    }

    @PostMapping(value = "/updateCart")
    public String updateCart(ShoppingCartDto shoppingCart) {
        shoppingCartService.updateItemsQuantity(shoppingCart.getShoppingItems());
        return "redirect:/shoppingCart";
    }

    @GetMapping(value = "/shoppingCart")
    public String getShoppingCart(Model model) {
        var shoppingCart = ShoppingCartDto.builder().shoppingItems(shoppingCartService.getShoppingCart()).build();
        model.addAttribute("shoppingCart", shoppingCart);

        return "shopping-cart";
    }

    @GetMapping(value = "/checkout")
    public String checkout(Model model) {
        model.addAttribute("addressDto", new AddressDto());

        return "checkout";
    }

    @PostMapping(value = "/sendOrder")
    public String sendOrder(@Valid AddressDto address, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("addressDto", address);
            return "checkout";
        }
        var orderDto = OrderDto.builder()
                .orderedProducts(shoppingCartService.getShoppingCart())
                .deliveryAddress(address)
                .createdAt(LocalDateTime.now())
                .customerId(getLoggedInUser())
                .build();
        try {
            orderController.createOrder(orderDto);
            shoppingCartService.clearCart();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/shoppingCart";
        }
        redirectAttributes.addFlashAttribute("message", "Order sent!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/home";
    }

    private String getLoggedInUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = principal instanceof UserDetails userDetails ? userDetails.getUsername() : principal.toString();
        var customer = customerController.getCustomerByUsername(username);
        return customer.getId();
    }
}
