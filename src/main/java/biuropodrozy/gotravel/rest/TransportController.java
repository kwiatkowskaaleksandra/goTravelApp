package biuropodrozy.gotravel.rest;/*
 * @project gotravel
 * @author kola
 */


import biuropodrozy.gotravel.model.Transport;
import biuropodrozy.gotravel.service.TransportService;
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
