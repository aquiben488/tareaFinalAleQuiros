/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "videojuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videojuegos.findAll", query = "SELECT v FROM Videojuegos v"),
    @NamedQuery(name = "Videojuegos.findByIdVideojuego", query = "SELECT v FROM Videojuegos v WHERE v.idVideojuego = :idVideojuego"),
    @NamedQuery(name = "Videojuegos.findByTitulo", query = "SELECT v FROM Videojuegos v WHERE v.titulo = :titulo"),
    @NamedQuery(name = "Videojuegos.findByFechaLanzamiento", query = "SELECT v FROM Videojuegos v WHERE v.fechaLanzamiento = :fechaLanzamiento")})
public class Videojuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVideojuego")
    private Integer idVideojuego;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaLanzamiento")
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVideojuego")
    private Collection<Reseñas> reseñasCollection;
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    @ManyToOne(optional = false)
    private Categorias idCategoria;

    public Videojuegos() {
    }

    public Videojuegos(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public Videojuegos(Integer idVideojuego, String titulo) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
    }

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @XmlTransient
    public Collection<Reseñas> getReseñasCollection() {
        return reseñasCollection;
    }

    public void setReseñasCollection(Collection<Reseñas> reseñasCollection) {
        this.reseñasCollection = reseñasCollection;
    }

    public Categorias getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categorias idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVideojuego != null ? idVideojuego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videojuegos)) {
            return false;
        }
        Videojuegos other = (Videojuegos) object;
        if ((this.idVideojuego == null && other.idVideojuego != null) || (this.idVideojuego != null && !this.idVideojuego.equals(other.idVideojuego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.Videojuegos[ idVideojuego=" + idVideojuego + " ]";
    }
    
}
