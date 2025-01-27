package org.tasks.reservation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tasks.reservation.config.ProjectConfig;
import org.tasks.reservation.helper.MenuLauncher;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var springContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
        System.out.println("Registered Beans: " + Arrays.toString(springContext.getBeanDefinitionNames()));
        MenuLauncher menuLauncher = springContext.getBean(MenuLauncher.class);
        menuLauncher.showMainMenu();
    }
}
