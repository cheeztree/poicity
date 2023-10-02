package poicity.service;

import org.springframework.security.core.userdetails.UserDetails;

import poicity.entity.User;

public interface JwtService {
	
	String getToken(UserDetails user);
	
	String getToken(User user);

	String getUsernameFromToken(String token);

	boolean isTokenValid(String token, UserDetails userDetails);

	public User decodeGoogleToken(String token);
		
}
