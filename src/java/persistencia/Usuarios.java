/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esteban
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUid", query = "SELECT u FROM Usuarios u WHERE u.uid = :uid"),
    @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuarios.findByUsername", query = "SELECT u FROM Usuarios u WHERE u.username = :username"),
    @NamedQuery(name = "Usuarios.findByIsAdmin", query = "SELECT u FROM Usuarios u WHERE u.isAdmin = :isAdmin")})
public class Usuarios implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    private byte[] passhash;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "uid")
    private Integer uid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username")
    private String username;
    @Column(name = "isAdmin")
    private Boolean isAdmin;

    public Usuarios() {
    }

    public Usuarios(Integer uid) {
	this.uid = uid;
    }

    public Usuarios(Integer uid, String nombre, String username, byte[] passhash) {
	this.uid = uid;
	this.nombre = nombre;
	this.username = username;
	this.passhash = passhash;
    }

    public Integer getUid() {
	return uid;
    }

    public void setUid(Integer uid) {
	this.uid = uid;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }


    public Boolean getIsAdmin() {
	return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (uid != null ? uid.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Usuarios)) {
	    return false;
	}
	Usuarios other = (Usuarios) object;
	if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "persistencia.Usuarios[ uid=" + uid + " ]";
    }

    public byte[] getPasshash() {
	return passhash;
    }

    public void setPasshash(byte[] passhash) {
	this.passhash = passhash;
    }
    
}
