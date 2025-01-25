package br.com.compass;

import br.com.compass.ui.Menu;
import br.com.compass.util.JpaUtil;

public class App {

    public static void main(String[] args) {
        try {
            Menu.run();
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }

}
