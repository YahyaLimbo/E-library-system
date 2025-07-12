package com.mycompany.treviska.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

@Component
public class Permissions {

    // Define permission mappings for different roles
    private static final Map<String, Set<String>> ROLE_PERMISSIONS = new HashMap<>();

    static {
        // ADMIN permissions - full access (matching your database role "admin")
        ROLE_PERMISSIONS.put("ROLE_ADMIN", Set.of(
            "CREATE_MATERIAL",
            "UPDATE_MATERIAL", 
            "DELETE_MATERIAL",
            "BULK_CREATE_MATERIAL",
            "VIEW_STATS",
            "UPLOAD_FILES",
            "EXPORT_MATERIALS",
            "VIEW_ALL_MATERIALS",
            "MANAGE_USERS"
        ));

        // USER permissions - basic access (matching your database role "user")
        ROLE_PERMISSIONS.put("ROLE_USER", Set.of(
            "VIEW_ALL_MATERIALS",
            "SEARCH_MATERIALS",
            "DOWNLOAD_FILES"
        ));
        
        // SUPPORT BOTH CASES: Add lowercase versions too
        ROLE_PERMISSIONS.put("ROLE_admin", Set.of(
            "CREATE_MATERIAL",
            "UPDATE_MATERIAL", 
            "DELETE_MATERIAL",
            "BULK_CREATE_MATERIAL",
            "VIEW_STATS",
            "UPLOAD_FILES",
            "EXPORT_MATERIALS",
            "VIEW_ALL_MATERIALS",
            "MANAGE_USERS"
        ));

        ROLE_PERMISSIONS.put("ROLE_user", Set.of(
            "VIEW_ALL_MATERIALS",
            "SEARCH_MATERIALS",
            "DOWNLOAD_FILES"
        ));
    }

    /**
     * Check if the authenticated user has a specific permission
     */
    public static boolean hasPermission(Authentication authentication, String permission) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // DEBUG: Print what we're checking
        System.out.println("=== PERMISSION CHECK DEBUG ===");
        System.out.println("Checking permission: " + permission);
        System.out.println("User authorities: " + authorities);
        
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println("Checking role: " + role);
            
            Set<String> rolePermissions = ROLE_PERMISSIONS.get(role);
            System.out.println("Role permissions: " + rolePermissions);
            
            if (rolePermissions != null && rolePermissions.contains(permission)) {
                System.out.println("Permission GRANTED for role: " + role);
                return true;
            }
        }
        
        System.out.println("Permission DENIED");
        return false;
    }

    /**
     * Check if user has any of the specified permissions
     */
    public static boolean hasAnyPermission(Authentication authentication, String... permissions) {
        for (String permission : permissions) {
            if (hasPermission(authentication, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has all of the specified permissions
     */
    public static boolean hasAllPermissions(Authentication authentication, String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(authentication, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all permissions for the authenticated user
     */
    public static Set<String> getUserPermissions(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Set.of();
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(ROLE_PERMISSIONS::get)
                .filter(perms -> perms != null)
                .flatMap(Set::stream)
                .collect(java.util.stream.Collectors.toSet());
    }

    /**
     * Check if user has a specific role
     */
    public static boolean hasRole(Authentication authentication, String role) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleWithPrefix));
    }
}