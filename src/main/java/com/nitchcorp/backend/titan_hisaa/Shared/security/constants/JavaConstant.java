package com.nitchcorp.backend.titan_hisaa.Shared.security.constants;

public class JavaConstant {

    public final static String FRONTEND_URL = "*";
    public final static String API_BASE_URL = "/api";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    public final static String[] PUBLIC_URLS = {
            "/users/**",
            API_BASE_URL + "/public/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/actuator/health",  // Health check pour Render
            "/actuator/info"
    };

    public final static String[] TITAN_ADMIN_URLS = {
            "/societies/**"
    };
    
    public final static String[] SOCIETY_ADMIN_AND_GENERAL_CASH_ADMIN_URLS = {
            
    };

    public final static String[] SOCIETY_ADMIN_URLS = {
        
    };
    public final static String[] GENERAL_CASH_ADMIN_URLS = {
            
    };
    public final static String[] STORE_ADMIN_URLS = {
            
    };
    public final static String[] CASHIER_URLS = {
            
    };
    public final static String[] CUSTUMER_URLS = {
            
    };

}
