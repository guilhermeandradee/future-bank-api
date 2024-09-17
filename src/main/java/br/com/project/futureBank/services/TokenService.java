package br.com.project.futureBank.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String tokenGenerate(String cpf){
        return Jwts.builder()
                .setSubject(cpf)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token, String cpf){
        try {
            String tokenCpf = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return tokenCpf.equals(cpf);
        } catch (Exception err){
            return false;
        }
    }
}
