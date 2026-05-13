package org.foodshare.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offres")
@CrossOrigin(origins = "*") // autorise React et Android
public class TestController {

    @GetMapping
    public List<Map<String, Object>> getOffres() {
        return List.of(
                Map.of("id", 1, "titre", "Test - Pizza invendue"),
                Map.of("id", 2, "titre", "Test - Panier légumes")
        );
    }
}