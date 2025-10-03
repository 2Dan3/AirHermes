package com.ftn.dan.airhermes.listeners;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Component
public final class InitServletContextInitializer implements ServletContextInitializer {

    /** kod koji se izvrsava po pokretanju aplikacije kada je ServletContext kreiran */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("Inicijalizacija konteksta pri ServletContextInitializer...");
//  todo
//        servletContext.setAttribute(FilmoviController.STATISTIKA_FILMOVA_KEY, new FilmStatistika());

        System.out.println("Uspeh ServletContextInitializer!");
    }

}