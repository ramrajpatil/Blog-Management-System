package com.blog.app.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 1000 * 60;

	private static final String SECRET = "dvhb23r87yrc87tqc784t87ct78ctgr8tbgcq7t7ctb87tbc87q4ntc87cqtnc87c78tc4b8b874tncq4ct87qc4tb87tb87tcbq87t48nc7qt48c7tqct8bn4qt8c7qc8tc"; // Secret key for signing JWT tokens

	// Method to extract username from the JWT token
	public String getUsernameFromToken(String token) {
		// Extract the subject (username) from the claims
		return getClaimFromToekn(token, Claims::getSubject);
	}

	// Method to extract expiration date from the JWT token
	public Date getExpirationDateFromToken(String token) {
		// Extract the expiration date from the claims
		return getClaimFromToekn(token, Claims::getExpiration);
	}

	// Method to extract specific claims from the JWT token using a resolver
	// function
	private <T> T getClaimFromToekn(String token, Function<Claims, T> claimResolver) {
		// Extract all claims from the token
		final Claims claims = getAllClaimsFromToken(token);
		// Apply the resolver function to the claims and return the result
		return claimResolver.apply(claims);
	}

	// Method to extract all claims from the JWT token
	private Claims getAllClaimsFromToken(String token) {
		// Set the signing key and parse the JWT token
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		// Extracts the body (claims) of JWT token
	}

	// Method to check if the JWT token is expired
	private Boolean isTokenExpired(String token) {
		// Checks if the token expiration date is before the current date
		return getExpirationDateFromToken(token).before(new Date());
	}

	// Method to generate a JWT token for the given username
	public String generateToken(String username) {
		// Create claims for the token (currently empty)
		Map<String, Object> claims = new HashMap<>();
		// Build and sign the JWT token with claims, subject, issuedAt, expiration, and
		// signing key
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 120)) // Token expires in 120 seconds
				.signWith(getSignKey(), SignatureAlgorithm.HS256) // Signing the token with HMAC SHA-256 algorithm
				.compact(); // Compact the JWT token into its final string representation
	}

	// White creating token
	// 1: Define claims of the token, like Issuer, Expiration, Subject and the ID.
	// 2: SigAlgorith the JWT using HS512 and secret key.
	// 3: According to JWS Compact Serialization 
	// Compaction of the JWT to a URL-safe string.
	
	
	// Method to get the signing key for JWT token
	private Key getSignKey() {
		// Decode the secret key from Base64
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		
		// Generate signing key using the secret key
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
//	// Method to get the signing key for JWT token
//    private Key getSignKey() {
//        // Generate a secure signing key
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    }

	// Method to validate the JWT token against UserDetails
	public Boolean validateToken(String token, UserDetails userDetails) {
		// Extract the username from the token
		final String username = getUsernameFromToken(token);
		// Validate if username matches UserDetails username and token is not expired
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

	}
}
