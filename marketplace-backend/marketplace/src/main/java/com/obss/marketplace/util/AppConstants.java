package com.obss.marketplace.util;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "2";
    public static final String[] PUBLIC_URLS = {
            "/api/auth/signup/**", "/api/public/categories/**", "/api/public/products/**", "/api/auth/login" };
    public static final String[] SELLER_URLS = {
            "/api/seller/**" };
    public static final String[] ADMIN_URLS = {
            "/api/admin/**"};
    public static final String[] USER_URLS = {
            "/api/public/user-special/**"};
}
