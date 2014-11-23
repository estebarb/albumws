/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author esteban
 */
@Path("roles")
public class RoleInfo {

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    /**
     * Creates a new instance of RoleInfo
     */
    public RoleInfo() {
    }

    /**
     * Retrieves representation of an instance of persistencia.service.RoleInfo
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
	if (securityContext.getUserPrincipal() != null) {
	    String username = securityContext.getUserPrincipal().getName();
	    //UsersFacadeREST u;
	    //u = new UsersFacadeREST();
	    //Users uData = u.find(username);
	    if (securityContext.isUserInRole("Administrador")) {
		//return new RolDescriptor(username, "administrador");
		//return "{\"rol\":\"Administrador\",\"username\":\""+username+"\",\"name\":\""+uData.getNombre()+"}";
		return "{\"rol\":\"Administrador\",\"username\":\""+username+"\"}";
	    } else if (securityContext.isUserInRole("Usuario")) {
		//return new RolDescriptor(username, "usuario");
		//return "{\"rol\":\"Usuario\",\"username\":\""+username+"\",\"name\":\""+uData.getNombre()+"}";
		return "{\"rol\":\"Usuario\",\"username\":\""+username+"\"}";
	    } else {
		//return new RolDescriptor("visita", "visitante");
		return "{\"rol\":\"visitante\", \"username\":\"visita\"}";
	    }
	} else {
	    //return new RolDescriptor("visita", "visitante");
	    return "{\"rol\":\"visitante\",\"username\":\"visita\"}";
	}
    }
}
