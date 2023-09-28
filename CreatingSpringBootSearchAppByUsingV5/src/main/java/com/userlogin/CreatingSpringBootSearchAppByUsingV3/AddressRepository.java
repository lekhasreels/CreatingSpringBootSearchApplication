package com.userlogin.CreatingSpringBootSearchAppByUsingV3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>{

	// Find addresses by street
    List<Address> findByStreetStartingWith(String street);

    // Find addresses by city
    List<Address> findByCityStartingWith(String city);
}
