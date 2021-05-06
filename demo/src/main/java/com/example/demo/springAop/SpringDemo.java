package com.example.demo.springAop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;;
public class SpringDemo {


    public static void main(String[]args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Alien a1 = context.getBean(Alien.class);
        a1.show();
        a1.showing("Himanshi");
    }

}
