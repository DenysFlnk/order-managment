package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByNameIgnoreCase(String name);

    @Transactional
    default void updateWithoutPassword(User user) {
        updateUserName(user.id(), user.getName());
        updateUserRoles(user.getId(), user.getRoles());
    }

    private void updateUserRoles(Integer userId, Set<Role> roles) {
        deleteUserRoles(userId);

        for (Role role : roles) {
            insertUserRoles(userId, role.name());
        }
    }

    @Modifying
    @Query("UPDATE User us SET us.name= :newName WHERE us.id= :userId")
    void updateUserName(int userId, String newName);

    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = :userId", nativeQuery = true)
    void deleteUserRoles(Integer userId);

    @Modifying
    @Query(value = "INSERT INTO user_role (user_id, role) VALUES (:userId, :role)", nativeQuery = true)
    void insertUserRoles(Integer userId, String role);
}
