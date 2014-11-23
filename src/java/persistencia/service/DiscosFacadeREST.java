/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.service;

import java.util.List;
import javax.annotation.security.PermitAll;
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
import persistencia.Discos;
import persistencia.Artistas;

/**
 *
 * @author esteban
 */
@Stateless
@Path("persistencia.discos")
@PermitAll
public class DiscosFacadeREST extends AbstractFacade<Discos> {

    @PersistenceContext(unitName = "ProyectoDiscosPU")
    private EntityManager em;

    public DiscosFacadeREST() {
	super(Discos.class);
    }

    @POST
    @Consumes({"application/json"})
    @RolesAllowed({"Usuario","Administrador"})
    public void create(Discos entity/*, @HeaderParam("username") String username*/) {
	//System.out.println("---->"+username);
	//System.out.println(entity.toString());
	if (entity.getArtista().getAid() == null) {
	    // Tal vez hay que crear el artista
	    em.persist(entity.getArtista());
	    List<Artistas> lstArtistas = (List<Artistas>) em.createNamedQuery("Artistas.findByNombre")
		    .setParameter("nombre", entity.getArtista().getNombre())
		    .getResultList();
	    for (Artistas newArtista : lstArtistas) {
		if (newArtista.getNombre().equals(entity.getArtista().getNombre())) {
		    entity.setArtista(newArtista);
		    em.persist(entity);
		    em.flush();
		    break;
		}
	    }
	} else {
	    super.create(entity);
	}
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @RolesAllowed({"Usuario","Administrador"})
    public void edit(@PathParam("id") Integer id, Discos entity) {
	if (entity.getArtista().getAid() == null) {
	    // Tal vez hay que crear el artista
	    em.persist(entity.getArtista());
	    Artistas newArtista = (Artistas) em.createNamedQuery("Artistas.findByNombre")
		    .setParameter("nombre", entity.getArtista().getNombre())
		    .getSingleResult();
	    entity.setArtista(newArtista);
	    em.flush();
	}
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
    public Discos find(@PathParam("id") Integer id) {
	return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Discos> findAll() {
	return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Discos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
