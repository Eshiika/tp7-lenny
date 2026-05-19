package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.user.*;
import fr.ekod.cda.ja.tp7.entity.User;
import fr.ekod.cda.ja.tp7.enums.Role;
import fr.ekod.cda.ja.tp7.exception.EmailAlreadyUsedException;
import fr.ekod.cda.ja.tp7.mapper.UserMapper;
import fr.ekod.cda.ja.tp7.repository.UserRepository;
import fr.ekod.cda.ja.tp7.security.CustomUserDetailsService;
import fr.ekod.cda.ja.tp7.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    public UserDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException(dto.email());
        }

        // Enregistre un nouvel utilisateur
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        // Hash le mot de passe avec BCrypt
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public TokenResponseDTO login(LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new TokenResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessExpirationSeconds()
        );
    }

    public TokenResponseDTO refresh(RefreshRequestDTO dto) {
        String refreshToken = dto.refreshToken();
        String email = jwtService.extractUsername(refreshToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Refresh token invalide");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new TokenResponseDTO(
                newAccessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessExpirationSeconds()
        );
    }

    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return userMapper.toDto(user);
    }

}
