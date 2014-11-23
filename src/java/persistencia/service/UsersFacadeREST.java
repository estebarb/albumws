package persistencia.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.*;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import persistencia.Users;

/**
 *
 * @author esteban
 */
@Stateless
@Path("persistencia.users")
@RolesAllowed("Administrador")
public class UsersFacadeREST extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "ProyectoDiscosPU")
    private EntityManager em;

    public UsersFacadeREST() {
	super(Users.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Users entity) {
	entity.setPassword(HashPassword(entity.getPassword()));
	super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") String id, Users entity) {
	entity.setPassword(HashPassword(entity.getPassword()));
	super.edit(entity);
    }
    
    private String HashPassword(String password){
	try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = "admin";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String output = bigInt.toString(16);

            return output;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No hay algoritmo", ex);
	    return "";
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
	super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Users find(@PathParam("id") String id) {
	return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Users> findAll() {
	return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
	return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
	return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }
    
}
