package com.tight.coupling;

public class UserManager {
    UserDatabase ud = new UserDatabase();

    public String getUserInfo(){
        return ud.getUserDetails();
    }
}
