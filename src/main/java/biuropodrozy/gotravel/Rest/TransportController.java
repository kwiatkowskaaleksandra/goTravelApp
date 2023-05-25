package biuropodrozy.gotravel.Rest;/*
 * @project gotravel
 * @author kola
 */


import biuropodrozy.gotravel.Model.Transport;
import biuropodrozy.gotravel.Service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transport")
public class TransportController {

    private final TransportService transportService;

    @GetMapping(value = "/all")
    ResponseEntity<List<Transport>> readAllTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }
}
