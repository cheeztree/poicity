package poicity.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import poicity.entity.User;
import poicity.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService{

	private final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

	@Override
	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user);
	}	

	@Override
	public String getToken(User user) {
		return getToken(new HashMap<>(), user);
	}	

	private String getToken(Map<String, Object> extraClaims, UserDetails user) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + daysInMillis(9999)))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private String getToken(Map<String, Object> extraClaims, User user) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				//				.setSubject(user.getUsername())
				.setSubject(user.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + daysInMillis(9999)))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//	private Key getKey() {
	//		byte[] keyBytes = Base64.decodeBase64(SECRET_KEY);
	//		SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
	//		return key;
	//	}


	@Override
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		boolean valid = false;
		try {
			valid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		} catch(Exception e) {
//			e.printStackTrace();
//        	System.err.println("Invalid email");

		}
		return valid;
	}

	@Override
	public User decodeGoogleToken(String token) {
		//		String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM3ZTExNDEwNTlhMTliMjE4MjA5YmM1YWY3YTgxYTcyMGUzOWI1MDAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI2MDc4ODQ3ODI5MjEtMnBndnZoaDdvZDZhZDYzbHJubTU5bmNwZGF1cjBoMGYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI2MDc4ODQ3ODI5MjEtMnBndnZoaDdvZDZhZDYzbHJubTU5bmNwZGF1cjBoMGYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTU3MjQyNDMxNzE3Nzc4NDg2NzAiLCJlbWFpbCI6Im1pbGFuby5taWNhZWxAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTY5MzkwNzk0MywibmFtZSI6Ik1pY2FlbGEgTWlsYW5vIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBY0hUdGVnaEZrM0FWWGNwNTRtQWg0SGs4Y19GYmhnaTNxM0NGbjl2VWJJWXVmWD1zOTYtYyIsImdpdmVuX25hbWUiOiJNaWNhZWxhIiwiZmFtaWx5X25hbWUiOiJNaWxhbm8iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTY5MzkwODI0MywiZXhwIjoxNjkzOTExODQzLCJqdGkiOiIyNjdlNjZhOTJkZWEzZGY5NGNiMjViZjVmMDBkZjdhNjk3NzQyMTY2In0.jT76X-0dg_2zBvcPQ9WaVXbIXee0yuTf3wwPVw3bHxrukdhjjLjXumHiCgCpNL3qO6PmPc94bYiDWe_iOagDPUslUcYq7dlBQuXgQANAORMuoJ3_J8MvgPSAcbOLow-auAMeL6if3aJ7MNRJmMcuTGpn3jYsrtsTuq7ZGia-LQHxClJMnFz8ywjHCyKjHjTZJ-AScCDRWj8z-MrV4w1v8sI2eK9I6akJ1x-vZneQp7ax6P7kbVSBXtRL2zoqN4d70G1oN1SJWpDp31dn3Bt11i6MdKJfRSFGPvtGB66ix6QtPWb0qmx9y1oKrLCPExtIrf94DgDFifF-e9rklvjLqQ";
		User user = new User();

		try {
			DecodedJWT jwt = JWT.decode(token);
			String payload = new String(Base64.decodeBase64(jwt.getPayload()), "UTF-8");
			String[] splitData = payload.split(",");
			for (String linea : splitData) {

				if(linea.contains("\"email\"")) {
					String asd = linea.replace("\"email\"", "");
					asd = asd.replace(":", "");
					asd = asd.replace("\"", "");

					user.setEmail(asd);
				}
				if(linea.contains("\"given_name\"")) {
					String asd = linea.replace("\"given_name\"", "");
					asd = asd.replace(":", "");
					asd = asd.replace("\"", "");

					user.setName(asd);
				}
				if(linea.contains("\"family_name\"")) {
					String asd = linea.replace("\"family_name\"", "");
					asd = asd.replace(":", "");
					asd = asd.replace("\"", "");

					user.setLastname(asd);
				}

			}

		} catch (JWTDecodeException | UnsupportedEncodingException exception){
			exception.printStackTrace();
		}

		return user;

	}

	private Claims getAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Date getExpiration(String token) {
		Date date = getClaim(token, Claims::getExpiration);
		System.out.println("EXPIRATION: " + date);
		return date;
		//		return getClaim(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date() );
	}

	private long daysInMillis(int days) {
		long l = 1000 * 60 * 60 * 24 * Long.valueOf(days);
		return l;
	}

	private long hoursInMillis(int hours) {
		long l = 1000 * 60 * 60 * Long.valueOf(hours);
		return l;
	}

}
