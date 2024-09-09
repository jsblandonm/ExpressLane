package com.ExpressLane.Service;

import com.ExpressLane.DTO.UserDTO;
import com.ExpressLane.Model.Role;
import com.ExpressLane.Model.RoleName;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.RoleRepository;
import com.ExpressLane.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    //Método para crear o guardar un usuario
    public UserDTO createUser(UserDTO userDTO){
        User user = mapToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //Convertir roles  de String a Entity Role
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName(RoleName.valueOf(roleName))
                    .orElseThrow(() -> new RuntimeException("Role not found with name" + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        User newUser = userRepository.save(user);
        return mapToDTO(newUser);
    }

    //Método para obtener todos lo usuarios
    public List<UserDTO> getAllUsers(){

        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());

//        List<User> users = userRepository.findAll();
//        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //Método para obtener una usuario por ID
    public UserDTO getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return mapToDTO(user.get());
        }
        throw new RuntimeException("User not found with id" + id);
    }

    //Método para actualizar un usuario existente
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id" + id));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
//        existingUser.setPassword(userDTO.getPassword());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        //Manejo de roles
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName(RoleName.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Role not found with name" + roleName)))
                .collect(Collectors.toSet());

        existingUser.setRoles(roles);

        User updateUser = userRepository.save(existingUser);
        return mapToDTO(updateUser);
    }

    //Mpetodo para eliminar un usuario por ID
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id" + id));
//        if (!user.getSentPackages().isEmpty() || !user.getReceivedPackages().isEmpty()) {
//            throw new RuntimeException("Cannot delete user with associated packages.");
//        }

        //Eliminar paquetes enviados y recibidos

        packageRepository.deleteAll(user.getSentPackages());
        packageRepository.deleteAll(user.getReceivedPackages());

        //Luego eliminar direcciones
        addressRepository.deleteAll(user.getAddresses());
        //Finalmente eliminar el usuario
        userRepository.delete(user);
    }

    //Método auxiliar para mapera entre ENTITY y DTO
    public User mapToEntity(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .password(userDTO.getPassword())
//                .roles(userDTO.getRoles() != null
//                ? userDTO.getRoles().stream()
//                        .map(roleName -> Role.builder().name(RoleName.valueOf(roleName)).build())
//                        .collect(Collectors.toSet())
//                        : new HashSet<>())// Asegura que roles no sea null
                .roles(new HashSet<>())
                .build();
    }

    public UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                //.password(user.getPassword()) Ocultar password
//                .roles(user.getRoles() != null
//                        ? user.getRoles().stream()
//                        .map(role -> role.getName().name())
//                        .collect(Collectors.toList())
//                        : new ArrayList<>()) // Asegura que roles no sea null
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()))
                .build();
    }
}
