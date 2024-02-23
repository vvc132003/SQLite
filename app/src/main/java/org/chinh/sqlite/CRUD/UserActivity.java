package org.chinh.sqlite.CRUD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.chinh.sqlite.R;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userRepository = new UserRepository(this);
        userRepository.open();
        add();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> userList = userRepository.getAllUsers();
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
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

    private class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

        private List<User> userList;

        public UserAdapter(List<User> userList) {
            this.userList = userList;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = userList.get(position);
            holder.textEmail.setText("Email: " + user.getEmail());
            holder.textRole.setText("Role: " + user.getRole());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView textEmail;
            TextView textRole;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                textEmail = itemView.findViewById(R.id.textEmail);
                textRole = itemView.findViewById(R.id.textRole);
            }
        }
    }
}
