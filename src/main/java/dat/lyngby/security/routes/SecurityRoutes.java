package dat.lyngby.security.routes;


import com.fasterxml.jackson.databind.ObjectMapper;
import dat.lyngby.security.enums.Role;
import dat.lyngby.utils.Utils;
import io.javalin.apibuilder.EndpointGroup;

import static dat.lyngby.security.controllers.SecurityController.getInstance;
import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
public class SecurityRoutes {
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static dat.lyngby.security.controllers.SecurityController securityController = getInstance();
    public static EndpointGroup getSecurityRoutes() {
        return ()->{
            path("/auth", ()->{
                get("/test", ctx->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from Open")), Role.ANYONE);
                post("/login", securityController.login(), Role.ANYONE);
                before(securityController.authenticate()); // check if there is a valid token in the header
                post("/register", securityController.register(), Role.ANYONE);
//                post("/authenticate", securityController.authenticate());
//                get("/logout", securityController.logout());
            });
        };
    }
    public static EndpointGroup getSecuredRoutes(){
        return ()->{
            path("/protected", ()->{
                before(securityController.authenticate()); // check if there is a valid token in the header
                get("/user_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")), Role.USER);
                get("/admin_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")), Role.ADMIN);
            });
        };
    }
}
