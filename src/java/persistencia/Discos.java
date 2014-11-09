/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esteban
 */
@Entity
@Table(name = "discos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discos.findAll", query = "SELECT d FROM Discos d"),
    @NamedQuery(name = "Discos.findByDid", query = "SELECT d FROM Discos d WHERE d.did = :did"),
    @NamedQuery(name = "Discos.findByTitulo", query = "SELECT d FROM Discos d WHERE d.titulo = :titulo"),
    @NamedQuery(name = "Discos.findByFecha", query = "SELECT d FROM Discos d WHERE d.fecha = :fecha")})
public class Discos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "did")
    private Integer did;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "artista", referencedColumnName = "aid")
    @ManyToOne(optional = false)
    private Artistas artista;

    public Discos() {
    }

    public Discos(Integer did) {
	this.did = did;
    }

    public Discos(Integer did, String titulo) {
	this.did = did;
	this.titulo = titulo;
    }

    public Integer getDid() {
	return did;
    }

    public void setDid(Integer did) {
	this.did = did;
    }

    public String getTitulo() {
	return titulo;
    }

    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    public Date getFecha() {
	return fecha;
    }

    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    public Artistas getArtista() {
	return artista;
    }

    public void setArtista(Artistas artista) {
	this.artista = artista;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (did != null ? did.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Discos)) {
	    return false;
	}
	Discos other = (Discos) object;
	if ((this.did == null && other.did != null) || (this.did != null && !this.did.equals(other.did))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "persistencia.Discos[ did=" + did + " ]";
    }
    
}
