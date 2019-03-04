package dominos.demo.controller;

import dominos.demo.model.DTOs.CommonResponseDTO;
import dominos.demo.model.daos.PizzaDao;
import dominos.demo.model.products.Pizza;
import dominos.demo.util.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
public class PizzaController extends BaseController{


    public static final double MIN_QUANTITY = 0;
    public static final double MAX_QUANTITY = 100;
    //session attribute
    public static final String SHOPPING_CART = "cart";
    public static final String USER = "user";



    @Autowired
    PizzaDao productDao;

    @PostMapping(value = "/pizzas/add")
    public CommonResponseDTO savePizza(@RequestBody Pizza pizza, HttpSession session) throws InvalidInputException  {
        // SessionManager.validateLoginAdmin(session);
        validatePizzaInput(pizza);
        productDao.addProduct(pizza);
        return  new CommonResponseDTO("Pizza with name: " + pizza.getName() + " is successfully added" , LocalDateTime.now());
    }

    @GetMapping(value = "/pizzas")
    public List<Pizza> getAllPizzas(){
        return productDao.getAllPizzas();
    }

    @GetMapping(value = "/pizzas/filter/name")
    public List<Pizza> filterByPrice(@RequestParam("name") String name) throws ProductException {
        List<Pizza> products = productDao.getAllPizzaNamesOrderedByPrice(name);
        if(products.isEmpty()) {
            throw new ProductException("No pizzas found by this name!");
        }
        return products;
    }


    @PutMapping(value = ("/pizzas/{id}/quantity/{quantity}"))
    public CommonResponseDTO changeProductQuantity(@PathVariable("id") long id, @PathVariable("quantity") int quantity, HttpSession session) throws BaseException {
       // SessionManager.validateLoginAdmin(session);
        if (quantity >= MIN_QUANTITY && quantity <= MAX_QUANTITY) {
            productDao.changePizzaQuantity(id, quantity);
        }
        else {
            throw new InvalidInputException("Quantity must be in interval : " + MIN_QUANTITY  +" - " + MAX_QUANTITY + " .");
        }
        return new CommonResponseDTO("Pizza with id: " + id + " has quantity : " + quantity + " ." , LocalDateTime.now());
    }

    private void validatePizzaInput(Pizza pizza)throws InvalidInputException {
        if(pizza.getName() == null || pizza.getName().isEmpty()
                || pizza.getDescription() == null || pizza.getDescription().isEmpty()
                || pizza.getSize() == null || pizza.getWeight() < 0 || pizza.getPrice() < 0){
            throw new InvalidInputException("Invalid input for the pizza. Please try again! All fields must be filled in!");
        }
    }
}
