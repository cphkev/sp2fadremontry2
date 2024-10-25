package dat.lyngby.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiProps {

    // == HIBERNATE CONFIG FILE ==
    public static final String DB_NAME = "sp2faedremon";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "postgres";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    public static final String DB_URL_MADS ="jdbc:postgresql://localhost:5433/" + DB_NAME;

    // == API CONFIG ==
    public static final int PORT = 7070;
    public static final String API_CONTEXT = "/api";

}
