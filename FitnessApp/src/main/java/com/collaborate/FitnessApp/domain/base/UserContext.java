package com.collaborate.FitnessApp.domain.base;

import com.collaborate.FitnessApp.domain.enums.Role;

public class UserContext {
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> role = new ThreadLocal<>();

    public static void setUserId(String id) {
        userId.set(id);
    }

    public static String getUserId() {
        return userId.get();
    }

    public static void setRole(String userRole) {
        role.set(userRole);
    }

    public static String getRole() {
        return role.get();
    }

    public static void clear() {
        userId.remove();
        role.remove();
    }

    public static String getAuditField() {
        if(getRole().equals(Role.ROLE_CLIENT.toString())){
            return "CLIENT : "+getUserId();
        }
        else if(getRole().equals(Role.ROLE_TRAINER.toString())){
            return "TRAINER : "+getUserId();
        }
        else if(getRole().equals(Role.ROLE_CENTER_ADMIN.toString())){
            return "ADMIN : "+getUserId();
        }
        else{
            return "SELF";
        }
    }
}
