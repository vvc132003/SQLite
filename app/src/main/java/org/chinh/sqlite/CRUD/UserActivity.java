package org.chinh.sqlite.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.chinh.sqlite.R;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userRepository = new UserRepository(this);
        userRepository.open();
        add();
        displayAllUsers();
        update();
        delete(1);
        userRepository.close();
    }

    private void add() {
        User sampleUser = new User("vvc132003@gmail.com ", "12345", "Admin");
        long userId = userRepository.addUser(sampleUser);
        Log.d("", "" + userId);
    }

    private void displayAllUsers() {
        List<User> userList = userRepository.getAllUsers();
        for (User user : userList) {
            Log.d("", "Email: " + user.getEmail() + ", Role:: " + user.getRole());
        }
    }

    private void update() {
        User existingUser = userRepository.getUserById(1);
        if (existingUser != null) {
            existingUser.setEmail("haha12@gmail.com");
            existingUser.setPassword("123");
            existingUser.setRole("Admin");

            int rowsAffected = userRepository.updateUser(existingUser);
            User updatedUser = userRepository.getUserById(existingUser.getId());
            if (updatedUser != null) {
                Log.d("UserActivity", "Updated user information:");
                Log.d("UserActivity", "Email: " + updatedUser.getEmail());
                Log.d("UserActivity", "Password: " + updatedUser.getPassword());
                Log.d("UserActivity", "Role: " + updatedUser.getRole());
            } else {
                Log.d("UserActivity", "User not found after update");
            }

            Log.d("UserActivity", "Updated " + rowsAffected + " rows");
        } else {
            Log.d("UserActivity", "User not found for update");
        }
    }

    private void delete(long userIdToDelete) {
        userRepository.deleteUser(userIdToDelete);
        Log.d("UserActivity", "Deleted user with ID: " + userIdToDelete);
    }
}
