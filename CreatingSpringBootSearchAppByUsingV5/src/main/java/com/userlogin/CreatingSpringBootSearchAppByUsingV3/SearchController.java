package com.userlogin.CreatingSpringBootSearchAppByUsingV3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
    private SearchService searchService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<User>> searchUsersByUsername(@PathVariable String username) {
        List<User> users = searchService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/address/{street}")
    public ResponseEntity<List<Address>> searchAddressesByStreet(@PathVariable String street) {
        List<Address> addresses = searchService.searchAddressesByStreet(street);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<User>> searchUsersByCity(@PathVariable String city) {
        List<User> users = searchService.searchUsersByCity(city);
        return ResponseEntity.ok(users);
    }
}
