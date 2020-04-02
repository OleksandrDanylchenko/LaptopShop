package ua.alexd.security;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, MANAGER, CEO;

    @NotNull
    @Contract(pure = true)
    @Override
    public String getAuthority() {
        return name();
    }
}