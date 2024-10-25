package dat.lyngby;

import dat.lyngby.config.ApplicationConfig;
import dat.lyngby.config.Populate;

public class Main {
    public static void main(String[] args) {
        Populate.Populate();
        ApplicationConfig.startServer(7070);
    }
}