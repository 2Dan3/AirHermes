package com.ftn.dan.airhermes.listeners;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@Component
public class InitHttpSessionListener implements HttpSessionListener {

    /** kod koji se izvrsava po kreiranju sesije */
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Inicijalizacija sesisje HttpSessionListener...");

        // pri kreiranju sesije inicijalizujemo je ili radimo neke dodatne aktivnosti
        HttpSession session  = event.getSession();
        System.out.println("Session id korisnika je "+ session.getId());
//  todo
//        session.setAttribute(FilmoviController.POSECENI_FILMOVI_ZA_KORISNIKA_KEY, new ArrayList<Film>());

        System.out.println("Uspeh HttpSessionListener!");
    }

    /** kod koji se izvrsava po brisanju sesije */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("Brisanje sesisje HttpSessionListener...");

        System.out.println("Uspeh HttpSessionListener!");
    }

}