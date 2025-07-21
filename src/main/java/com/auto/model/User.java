package com.auto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.auto.config.EnvConfig;
import com.auto.utils.Utils;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String password;
    private String country;
    private String address;
    private String city;
    private String phoneNumber;

    public static User defaultUser() {
        return new User(
                "Le",
                "My",
                "my.le",
                initEmail(),
                initPassword(),
                "Vietnam",
                "Random address",
                "Random city",
                "0123456789"
        );
    }

    private static String initEmail() {
        String username = EnvConfig.getEmail();
        if (Objects.equals(username, "")) {
            return Utils.emailGenerator();
        }
        return username;
    }

    private static String initPassword() {
        String password = EnvConfig.getPassword();
        if (Objects.equals(password, "")) {
            return "password";
        }
        return password;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "User email = " + email;
    }
}
