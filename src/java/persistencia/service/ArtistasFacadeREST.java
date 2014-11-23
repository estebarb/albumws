/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia.service;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import persistencia.Artistas;

/**
 *
 * @author esteban
 */
@Stateless
@Path("persistencia.artistas")
public class ArtistasFacadeREST extends AbstractFacade<Artistas> {
    @PersistenceContext(unitName = "ProyectoDiscosPU")
    private EntityManager em;

    public ArtistasFacadeREST() {
	super(Artistas.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    @RolesAllowed({"Usuario","Administrador"})
    public void create(Artistas entity) {
	super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @RolesAllowed({"Usuario","Administrador"})
    public void edit(@PathParam("id") Integer id, Artistas entity) {
	super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"Usuario","Administrador"})
    public void remove(@PathParam("id") Integer id) {
	super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Artistas find(@PathParam("id") Integer id) {
	return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Artistas> findAll() {
	return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Artistas> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
	return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
	return String.valueOf(super.count());
    }
    
    @GET
    @Path("search/{query}")
    @Produces({"application/json"})
    public List<Artistas> Search(@PathParam("query") String query) {	
	final String sql = "SELECT a FROM Artistas a WHERE a.nombre LIKE ?1";
	Query qqq = em.createQuery(sql);
	System.out.println(qqq.toString());
	return qqq.setParameter(1, query+"%").getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }
    
}
