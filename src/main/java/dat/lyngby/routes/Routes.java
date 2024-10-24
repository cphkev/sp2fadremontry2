package dat.lyngby.routes;


import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {


    private final CardRoutes cardRoutes = new CardRoutes();
    private final PackRoutes packRoutes = new PackRoutes();



    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/cards", cardRoutes.getRoutes());
            path("/packs", packRoutes.getRoutes());
        };
    }
}
