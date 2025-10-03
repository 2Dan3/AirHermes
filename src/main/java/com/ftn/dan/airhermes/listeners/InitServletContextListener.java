package com.ftn.dan.airhermes.listeners;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class InitServletContextListener implements ServletContextListener {

    /** kod koji se izvrsava po nakon inicijalizacije ServletContext objekta */
    public void contextInitialized(ServletContextEvent event)  {
        System.out.println("Azuriranje konteksta ServletContextListener...");

        System.out.println("Uspeh ServletContextListener!");
    }

    /** kod koji se izvrsava po nakon uništavanja ServletContext objekta */
    public void contextDestroyed(ServletContextEvent event)  {
        System.out.println("Brisanje konteksta ServletContextListener...");

        System.out.println("Uspeh ServletContextListener!");
    }
}