package com.userlogin.CreatingSpringBootSearchAppByUsingV3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DataSource dataSource;
    public boolean testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameStartingWith(username);
    }

    public List<Address> searchAddressesByStreet(String street) {
        return addressRepository.findByStreetStartingWith(street);
    }

    public List<Address> searchAddressesByCity(String city) {
        return addressRepository.findByCityStartingWith(city);
    }

	public List<User> searchUsersByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
