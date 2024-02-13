package biuropodrozy.gotravel.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripFilteringRequest {

    private String typeOfTrip;
    private int idCountry;
    private int typeOfTransport;
    private double minPrice;
    private double maxPrice;
    private int minDays;
    private int maxDays;
}
