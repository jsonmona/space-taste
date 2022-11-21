package cf.spacetaste.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    final String jwtSecret = System.getenv("JWT_SECRET");

    public TokenService() {
        if (jwtSecret == null || jwtSecret.length() == 0) {
            throw new IllegalArgumentException("JWT_SECRET is empty!");
        }
    }

    /***
     * Checks if a token is valid.
     * @param token JWT token
     * @return userId of the token (0 if invalid)
     */
    public int checkToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer("space-taste.cf")
                    .withClaimPresence("uid")
                    .build()
                    .verify(token)
                    .getClaim("uid")
                    .asInt();
        } catch (JWTDecodeException e) {
            return 0;
        } catch (NullPointerException e) {
            // asInt may return null
            return 0;
        }
    }

    /**
     * Create a token for specified userId.
     * @param userId target userId
     * @return Created token
     */
    public String createToken(int userId) {
        Instant now = Instant.now();

        return JWT.create()
                .withIssuer("space-taste.cf")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(14*24*60*60))  // 2 weeks
                .withClaim("uid", userId)
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}
