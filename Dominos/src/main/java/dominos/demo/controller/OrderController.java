package dominos.demo.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import dominos.demo.model.DTOs.CommonResponseDTO;
import dominos.demo.model.daos.PizzaOrderDao;
import dominos.demo.model.users.User;
import dominos.demo.util.exceptions.BaseException;
import dominos.demo.util.exceptions.InvalidLogInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
public class OrderController extends BaseController {

    @Autowired
    PizzaOrderDao pizzaOrderDao;


    @PostMapping(value ="/order/restaurants/id")
    public CommonResponseDTO finishOrderForPizzaFromRestaurant(@RequestParam ("id") Long restaurant_id
                                                              /*@RequestBody LocalDateTime delivery_time*/,
                                                              HttpSession session)
    throws BaseException {
        if(SessionManager.isLoggedIn(session)) {
            pizzaOrderDao.orderPizzaFromRestaurant(restaurant_id, session);
            return new CommonResponseDTO("Successfull order!", LocalDateTime.now());
        }
        throw new InvalidLogInException("You are not logged in! Please log in to continue!");
    }

    @PostMapping(value ="/order/address/city/street")
    public CommonResponseDTO finishOrderForPizzaToAddress(@RequestParam  String city,
                                                               @RequestParam String street,
                                                               HttpSession session)
            throws BaseException {
        if(SessionManager.isLoggedIn(session)) {
            pizzaOrderDao.orderPizzaToAddress(city, street, session);
            return new CommonResponseDTO("Successfull order! .. ", LocalDateTime.now()); //TODO
        }
        throw new InvalidLogInException("You are not logged in! Please log in to continue!");
    }

}
