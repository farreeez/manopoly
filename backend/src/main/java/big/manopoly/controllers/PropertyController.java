package big.manopoly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import big.manopoly.services.PropertyService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/cardActions")
public class PropertyController {
    
    private final PropertyService cardActionService;
    
    @Autowired
    public PropertyController(PropertyService cardActionService) {
        this.cardActionService = cardActionService;
    }
    
    @PostMapping("/buyProperty")
    public ResponseEntity<?> buyProperty(@CookieValue(value = "playerId", defaultValue = "") String cookie) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return cardActionService.buyProperty(cookie);
    }
    
    @GetMapping("/getProperty/{id}")
    public ResponseEntity<?> getProperty(@PathVariable String id) {
        return cardActionService.getProperty(id);
    }


    @PostMapping("/mortgageProperty/{propertyId}")
    public ResponseEntity<?> mortgageProperty(@CookieValue(value = "playerId", defaultValue = "") String cookie, @PathVariable String propertyId) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return cardActionService.mortgageProperty(propertyId, cookie);
    }

    @PostMapping("/demortgageProperty/{propertyId}")
    public ResponseEntity<?> demortgageProperty(@CookieValue(value = "playerId", defaultValue = "") String cookie, @PathVariable String propertyId) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return cardActionService.demortgageProperty(propertyId, cookie);
    }

    @GetMapping("/isSetMortgaged/{id}")
    public ResponseEntity<?> isSetMortgaged(@PathVariable String id) {
        return cardActionService.isSetMortgaged(id);
    }

    @GetMapping("/doesPropertyHaveHotel/{id}")
    public ResponseEntity<?> doesPropertyHaveHotel(@PathVariable String id) {
        return cardActionService.doesPropertyHaveHotel(id);
    }

    @PostMapping("/buyHouse/{propertyId}")
    public ResponseEntity<?> buyHouse(@CookieValue(value = "playerId", defaultValue = "") String cookie, @PathVariable String propertyId) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return cardActionService.houseHelper(propertyId, cookie, true);
    }

    @PostMapping("/sellHouse/{propertyId}")
    public ResponseEntity<?> sellHouse(@CookieValue(value = "playerId", defaultValue = "") String cookie, @PathVariable String propertyId) {
        if (cookie.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return cardActionService.houseHelper(propertyId, cookie, false);
    }
}