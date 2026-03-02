package com.loose.coupling;

public class UserManager {
    private UserDataProvider udp;

    public UserManager(UserDataProvider udp) {
        this.udp = udp;
    }

    public String getUserInfo(){
        return udp.getUserDetails();
    }
}
