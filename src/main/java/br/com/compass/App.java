package br.com.compass;

import br.com.compass.ui.MainMenu;
import br.com.compass.util.JpaUtil;

public class App {

    public static void main(String[] args) {
        try {
            MainMenu.run();
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }

}
