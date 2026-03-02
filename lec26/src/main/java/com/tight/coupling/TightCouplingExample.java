package com.tight.coupling;

public class TightCouplingExample {
    public static void main(){
        UserManager um = new UserManager();
        System.out.println(um.getUserInfo());
    }
}
