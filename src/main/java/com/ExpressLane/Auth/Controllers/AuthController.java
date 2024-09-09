package com.ExpressLane.Auth.Controllers;

import com.ExpressLane.Auth.DTO.AuthCredentialsDTO;
import com.ExpressLane.DTO.UserDTO;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;

  @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsDTO authCredentialsDTO) {
      //System.out.println("Attempting to login with email: " + authCredentialsDTO.getEmail());

      // La autenticación se maneja en JWTAuthenticationFilter
      // Este método solo se llama si la autenticación fue exitosa
      User user = userRepository.findOneByEmail(authCredentialsDTO.getEmail())
              .orElseThrow(() -> new RuntimeException("User not found"));

      //Convertimos el User a UserDTO
      UserDTO userDTO = new UserDTO(
              user.getId(),
              user.getName(),
              user.getEmail(),
              user.getPhone(),
              null, // No devolver la contraseña en el token
              user.getRoles().stream().map(
                              role -> role.getName().name())
                      .collect(Collectors.toList()),
              user.getCreatedAt()
      );

      // Return the token
      return ResponseEntity.ok().body(userDTO);

//      try {
//          // Authenticate the user
//          Authentication auth = authenticationManager.authenticate(
//                  new UsernamePasswordAuthenticationToken(
//                          authCredentialsDTO.getEmail(),
//                          authCredentialsDTO.getPassword())
//          );
//
//          System.out.println("Authentication successful for: " + authCredentialsDTO.getEmail());
//
//          //si la auth est ok, on genere un token details the user
//          UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//
//          //Get  UserDto basasdo en el User de la BD
//          User user = userRepository.findOneByEmail(userDetails.getUsername())
//                  .orElseThrow(() -> new RuntimeException("User not found"));



          // Create the token
          //string token = tokenUtils.generateToken(user.getName(), user.getEmail(), userDTO.getRoles());


//      } catch (Exception e) {
//          e.printStackTrace();
//          System.out.println("Authentication failed: " + e.getMessage());
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//      }
  }
}
