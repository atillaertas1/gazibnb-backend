package com.shadowfax.vacationbookingsystem.service;

import com.shadowfax.vacationbookingsystem.model.User;
import com.shadowfax.vacationbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User registerUser(User user) {
        // E-posta adresi daha önce kaydedilmiş mi diye kontrol edebiliriz
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already taken.");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Şifreyi şifrele
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Kullanıcıyı kaydet
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id); // Optional Class oluşturuyoruz null için
        if(optionalUser.isPresent()){ //containerin var olup olmadığını kontrol ediyorus
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setEmailVerified(userDetails.getEmailVerified());
            user.setImage(userDetails.getImage());
            user.setPassword(userDetails.getPassword());
            user.setCreatedAt(userDetails.getCreatedAt());
            user.setUpdatedAt(userDetails.getUpdatedAt());
            user.setFavorites(userDetails.getFavorites());
            return userRepository.save(user);
        }
        return null; //null check için
    }

    public boolean deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.orElse(null);
    }

    public String verify(User user) {
        System.out.println("verify username: " + user.getUsername());
        System.out.println("verify password: " + user.getPassword());
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                // Token üretme
                return jwtService.generateToken(user.getUsername());
            } else {
                // Kullanıcı doğrulama başarısız
                return "Not success";
            }
        } catch (AuthenticationException e) {
            // Hata logu ekleyebilirsiniz
            System.out.println("Authentication failed: " + e.getMessage());
            return "Authentication failed";
        } catch (Exception e) {
            // Genel hata yönetimi
            System.out.println("An error occurred: " + e.getMessage());
            return "An error occurred";
        }
    }

    public User findByUsername(String username) { return  userRepository.findByUsername(username);}

}
