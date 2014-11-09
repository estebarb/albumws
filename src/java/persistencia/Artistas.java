/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author esteban
 */
@Entity
@Table(name = "artistas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artistas.findAll", query = "SELECT a FROM Artistas a"),
    @NamedQuery(name = "Artistas.findByAid", query = "SELECT a FROM Artistas a WHERE a.aid = :aid"),
    @NamedQuery(name = "Artistas.findByNombre", query = "SELECT a FROM Artistas a WHERE a.nombre = :nombre")})
public class Artistas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "aid")
    private Integer aid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artista")
    private Collection<Discos> discosCollection;

    public Artistas() {
    }

    public Artistas(Integer aid) {
	this.aid = aid;
    }

    public Artistas(Integer aid, String nombre) {
	this.aid = aid;
	this.nombre = nombre;
    }

    public Integer getAid() {
	return aid;
    }

    public void setAid(Integer aid) {
	this.aid = aid;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Discos> getDiscosCollection() {
	return discosCollection;
    }

    public void setDiscosCollection(Collection<Discos> discosCollection) {
	this.discosCollection = discosCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (aid != null ? aid.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Artistas)) {
	    return false;
	}
	Artistas other = (Artistas) object;
	if ((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "persistencia.Artistas[ aid=" + aid + " ]";
    }
    
}
