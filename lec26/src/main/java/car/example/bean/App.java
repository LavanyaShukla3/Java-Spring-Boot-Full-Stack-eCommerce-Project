package car.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args){
        //Start Spring
        //Read the XML file
        //Create beans
        //Store them
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationBeanContext.xml");

        //Spring,
        //Give me the bean with ID = myBean
        MyBean myBean = (MyBean) context.getBean("myBean");
        System.out.println(myBean);
    }

}
