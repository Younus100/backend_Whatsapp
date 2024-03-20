package com.whatsapp.backend.repository;

import com.whatsapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByEmailContainingIgnoreCase(String query);

    List<User> findByFullNameLikeIgnoreCaseOrEmailLikeIgnoreCase(String fullname,String email);

    User findByEmail(String email);
}
