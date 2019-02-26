package dominos.demo.model.users;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String street;
    //private Set<Order> myOrders;

    public User(String first_name, String last_name, String email, String password, String phone, String city, String street) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.street = street;
    }
}
