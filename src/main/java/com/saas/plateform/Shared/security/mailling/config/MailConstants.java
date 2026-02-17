package com.saas.plateform.Shared.security.mailling.config;

public class MailConstants {

    public static final String COMPANY_NAME = "Paiya";
    public static final String COMPANY_EMAIL = "contact@nitchcorp.tech"; // SMTP username
    public static final String COMPANY_SUPPORT = "support@nitchcorp.tech";
    public static final String WEBSITE_URL = "http://localhost:9081/api/swagger-ui/index.html#/";

    public static final String EMAIL_SUBJECT_WELCOME =
            "Bienvenue chez Titan - Vos informations de compte";
    public static final String EMAIL_FROM = COMPANY_EMAIL; // sender address


    public static final String TEMPLATE_ADMIN_CREATION = "email-admin-creation";

    // Society email templates
    public static final String TEMPLATE_SOCIETY_CREATION = "email-society-creation";
    public static final String TEMPLATE_SOCIETY_ACTIVATION = "email-society-activation";
    public static final String TEMPLATE_SOCIETY_DEACTIVATION = "email-society-deactivation";

}
