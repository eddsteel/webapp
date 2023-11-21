# Example Webapp

Example dropwizard webapp to show the change in routing behavior between Jersey 2.40 and 2.41 (dropwizard 3.0.3 and 3.0.4). It seems that regex path parameters are now optional.

## Example:

```sh
$ nix-shell -p maven

[nix-shell:~/src/webapp]$ DW_VERSION=3.0.3 mvn -q clean package

[nix-shell:~/src/webapp]$ java -jar target/webapp-1.0-SNAPSHOT.jar server >/dev/null &
[1] 95536

[nix-shell:~/src/webapp]$ curl localhost:8080/
/ endpoint hit.

[nix-shell:~/src/webapp]$ curl localhost:8080/hello
/:name endpoint hit with name: hello.

[nix-shell:~/src/webapp]$ DW_VERSION=3.0.4 mvn -q clean package

[nix-shell:~/src/webapp]$ java -jar target/webapp-1.0-SNAPSHOT.jar server >/dev/null &
[1] 95589

[nix-shell:~/src/webapp]$ curl localhost:8080/
/:name endpoint hit with name: null.

[nix-shell:~/src/webapp]$ curl localhost:8080/hello
/:name endpoint hit with name: hello.
```

This is with the following resource registered:

```java
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
}```
