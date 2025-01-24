package org.tasks.reservation;

public class Main {

    public static void main(String[] args) {
        MenuLauncher menuLauncher = new MenuLauncher(new Repository());
        menuLauncher.showMainMenu();
    }
}
