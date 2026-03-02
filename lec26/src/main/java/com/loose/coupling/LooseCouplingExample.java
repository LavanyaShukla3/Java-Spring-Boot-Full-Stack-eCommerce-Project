package com.loose.coupling;

public class LooseCouplingExample {
    public static void main(){
        UserDataProvider udp = new UserDatabaseProvider();
        UserManager umWithDB = new UserManager(udp);
        System.out.println(umWithDB.getUserInfo());

        UserDataProvider webServiceProvider = new WebServiceDataProvider();
        UserManager umWithWebService = new UserManager(webServiceProvider);
        System.out.println(umWithWebService.getUserInfo());
    }
}
