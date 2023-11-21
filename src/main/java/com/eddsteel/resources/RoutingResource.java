package com.eddsteel.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.TEXT_PLAIN)
@Path("/")
public class RoutingResource {
    @Path("")
    @GET
    public String rootEndpoint() {
        return "/ endpoint hit.";
    }

    @Path("{name: [a-z0-9]{3,128}}")
    @GET
    public String nameEndpoint(@PathParam("name") String name) {
        return String.format("/:name endpoint hit with name: %s.", name);
    }
}