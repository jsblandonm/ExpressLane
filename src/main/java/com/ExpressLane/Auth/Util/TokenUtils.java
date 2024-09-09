package com.ExpressLane.Auth.Util;

import com.ExpressLane.Model.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class TokenUtils {

    private static final String ACCESS_TOKEN_SECRET = "LJIUOiIwjEuZmFnSr1OxXnpx+b2xE9DYKE2soAAKKrQ=";
    private final static Long ACCESS_TOKEN_EXPIRATION = 2_592_000L;

    public static String generateToken(String name,String email, List<RoleName> roles) {
        long expirationTime = ACCESS_TOKEN_EXPIRATION * 1000;
        Date exiprationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Crear un mapa para los campos extra que se quieren incluir en el token
        Map<String, Object> extra = new HashMap<>();
        //extra.put("id", id); // Agregar el ID del usuario al token
        extra.put("name", name);
        extra.put("roles", roles.stream().map(RoleName::name).collect(Collectors.toList()));
        //extra.put("roles", roles); //Asegura que incuye los roles en el token

        // Convertir roles del enum a una lista de Strings
        List<String> rolesAsString = roles.stream()
                .map(RoleName::name)    // Convertir RoleName a String
                .collect(Collectors.toList());

        extra.put("roles", rolesAsString);// Añadir los roles al mapa extra

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(exiprationDate)
                .addClaims(extra) // Añadir los claims extra al token
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())) // Firmar el token
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            //Obtener email y nombre del usuario
            String email = claims.getSubject();
            String name = (String) claims.get("name");

            // Extraer roles del token y convertirlos en enum RoleName
            List<String> rolesAsString = claims.get("roles", List.class); // Roles en formato String

            // Convertir los roles a GrantedAuthority
            List<GrantedAuthority> authorities = rolesAsString.stream()
//                    .map(role -> RoleName.valueOf(role)) // Convertir String a RoleName enum
                    .map(SimpleGrantedAuthority::new) // Convertir RoleName a GrantedAuthority
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        } catch (JwtException   e) {
            return null;
        }
    }
}
