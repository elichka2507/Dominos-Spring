package dominos.demo.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDTO {

    private String city;
    private String street;

    @Override
    public String toString() {
        return city + " " + street;
    }
}
