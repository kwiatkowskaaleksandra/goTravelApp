package biuropodrozy.gotravel.service.impl.validation;

import biuropodrozy.gotravel.model.User;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidationData {
    private User user;
    private int numberOfChildren;
    private int numberOfAdults;
    private LocalDate departureDate;
    private int idAccommodation;
    private int idCity;
    private int numberOfDays;
}
