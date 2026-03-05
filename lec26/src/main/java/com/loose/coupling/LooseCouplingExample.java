package com.loose.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LooseCouplingExample {
    public static void main(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationIocLooseCoupling.xml");

        UserDataProvider udp = new UserDatabaseProvider();
        UserManager umWithDB = new UserManager(udp);
        System.out.println(umWithDB.getUserInfo());


        UserDataProvider webServiceProvider = new WebServiceDataProvider();
        UserManager umWithWebService = new UserManager(webServiceProvider);
        System.out.println(umWithWebService.getUserInfo());

        UserManager manager =
                (UserManager) context.getBean("userManagerWithDB");
        System.out.println(manager.getUserInfo());


    }
}
