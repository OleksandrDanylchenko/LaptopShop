package ua.alexd.security;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MANAGER;

    @NotNull
    @Contract(pure = true)
    @Override
    public String getAuthority() {
        return name();
    }
}