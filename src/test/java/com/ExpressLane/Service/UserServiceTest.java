package com.ExpressLane.Service;

import com.ExpressLane.DTO.UserDTO;
import com.ExpressLane.Model.Role;
import com.ExpressLane.Model.RoleName;
import com.ExpressLane.Model.User;
import com.ExpressLane.Repository.AddressRepository;
import com.ExpressLane.Repository.PackageRepository;
import com.ExpressLane.Repository.RoleRepository;
import com.ExpressLane.Repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Usamos la extensión de Mockito
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UserService userService; // Injectamos el servicio UserService


    @Test
    void createUser() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("Password23")
                .roles(List.of("ROLE_USER"))
                .build();

        Role userRole = new Role(1L, RoleName.ROLE_USER);
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("encodedPassword")
                .roles(new HashSet<>(Collections.singletonList(userRole)))
                .build();

        // Mock behaviors
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.createUser(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
        assertEquals(Collections.singletonList("ROLE_USER"), result.getRoles());

        // Verify interactions
        verify(passwordEncoder).encode("Password23");
        verify(roleRepository).findByName(RoleName.ROLE_USER);
        verify(userRepository).save(any(User.class));
    }


    @Test
    void getAllUsers() {

        // Arrange
        List<User> usersList = Arrays.asList(
                User.builder().id(1L).name("User1").email("user1@test.com").password("password").phone("123456").roles(new HashSet<>()).build(),
                User.builder().id(2L).name("User2").email("user2@test.com").password("passwordaa").phone("465123").roles(new HashSet<>()).build()
        );
        when(userRepository.findAll()).thenReturn(usersList);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getName());
        assertEquals("User2", result.get(1).getName());

        // Verify interactions
        verify(userRepository).findAll();

    }

    @Test
    void getUserById() {

        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("123456789")
                .roles(new HashSet<>())
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("123456789", result.getPhone());

        // Verify interactions
        verify(userRepository).findById(userId);

    }

    @Test
    void getUserById_NotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser() {

        // Arrange
        Long userId = 1L;
        UserDTO updateDTO = UserDTO.builder()
                .name("Updated User")
                .email("updated@test.com")
                .phone("987654")
                .password("123456")
                .roles(List.of("ROLE_USER"))
                .build();
        User existingUser = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@test.com")
                .password("123456")
                .roles(new HashSet<>())
                .build();
        Role role = new Role(1L, RoleName.ROLE_USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);


        // Act
        UserDTO result = userService.updateUser(userId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated User", result.getName());
        assertEquals("updated@test.com", result.getEmail());
        assertEquals("987654", result.getPhone());
        assertEquals(Collections.singletonList("ROLE_USER"), result.getRoles());

        // Verify interactions
        verify(userRepository).findById(userId);
        verify(roleRepository).findByName(RoleName.ROLE_USER);
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));

    }

    @Test
    void deleteUser() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@test.com")
                .password("123456")
                .roles(new HashSet<>(List.of(new Role(1L, RoleName.ROLE_USER))))
                .deleted(false)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        assertTrue(user.isDeleted());
        assertEquals(List.of(RoleName.ROLE_USER), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        // Verify interactions
        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void mapToEntity() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("Password23")
                .roles(List.of(RoleName.ROLE_USER.name()))
                .build();

        Role userRole = new Role(1L, RoleName.ROLE_USER);
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(userRole));
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("encodedPassword")
                .roles(new HashSet<>(Collections.singletonList(userRole)))
                .build();

        // Act
        User result = userService.mapToEntity(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("123456789", result.getPhone());

        assertEquals(Collections.singletonList("ROLE_USER"),
                result.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        // Verify interactions
        verify(passwordEncoder).encode("Password23");
        verify(roleRepository).findByName(RoleName.ROLE_USER);
    }

    @Test
    void mapToDTO() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .phone("123456789")
                .password("123456789")
                .roles(new HashSet<>())
                .build();
        Role role = new Role(1L, RoleName.ROLE_USER);
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        // Act
        UserDTO result = userService.mapToDTO(user);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
        assertEquals(Collections.singletonList("ROLE_USER"), result.getRoles());

    }
}