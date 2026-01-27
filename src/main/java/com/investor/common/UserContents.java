package com.investor.common;


public class UserContents {
    private static final ThreadLocal<Long> userIdThreadLocal=new ThreadLocal<>();
    private static  final ThreadLocal<String> userRoleThreadLocal=new ThreadLocal<>();
    private static  final ThreadLocal<String> userNameThreadLocal=new ThreadLocal<>();

    public static void setUserId(Long userId){
        userIdThreadLocal.set(userId);
    }
    public static Long getUserId(){
        return userIdThreadLocal.get();
    }
    public static void setUserRole(String role){
        userRoleThreadLocal.set(role);
    }
    public static String getUserRole(){
        return userRoleThreadLocal.get();
    }
    public static void setUserName(String name){
        userNameThreadLocal.set(name);
    }
    public static String getUserName(){
        return userNameThreadLocal.get();
    }
    public static void remove(){
        userRoleThreadLocal.remove();
        userIdThreadLocal.remove();
        userNameThreadLocal.remove();
    }
}
