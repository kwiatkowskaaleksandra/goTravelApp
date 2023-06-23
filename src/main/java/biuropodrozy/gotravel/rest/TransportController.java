package biuropodrozy.gotravel.rest;

import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Transport controller.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transport")
public class TransportController {

    /**
     * Service for managing Transport-related operations.
     */
    private final TransportService transportService;

    /**
     * Read all Transports response entity.
     *
     * @return the response entity
     */
    @GetMapping(value = "/all")
    ResponseEntity<List<Transport>> readAllTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }
}
