/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw;

import jakarta.persistence.Basic;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "rese\u00f1as")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rese\u00f1as.findAll", query = "SELECT r FROM Rese\u00f1as r"),
    @NamedQuery(name = "Rese\u00f1as.findByIdRese\u00f1a", query = "SELECT r FROM Rese\u00f1as r WHERE r.idRese\u00f1a = :idRese\u00f1a"),
    @NamedQuery(name = "Rese\u00f1as.findByPuntuacion", query = "SELECT r FROM Rese\u00f1as r WHERE r.puntuacion = :puntuacion"),
    @NamedQuery(name = "Rese\u00f1as.findByFechaRese\u00f1a", query = "SELECT r FROM Rese\u00f1as r WHERE r.fechaRese\u00f1a = :fechaRese\u00f1a"),
    @NamedQuery(name = "Rese\u00f1as.findByUtiles", query = "SELECT r FROM Rese\u00f1as r WHERE r.utiles = :utiles"),
    @NamedQuery(name = "Rese\u00f1as.findBySpoilers", query = "SELECT r FROM Rese\u00f1as r WHERE r.spoilers = :spoilers")})
public class Reseñas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRese\u00f1a")
    private Integer idReseña;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "puntuacion")
    private BigDecimal puntuacion;
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "fechaRese\u00f1a")
    @Temporal(TemporalType.DATE)
    private Date fechaReseña;
    @Column(name = "utiles")
    private Integer utiles;
    @Column(name = "spoilers")
    private Boolean spoilers;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;
    @JoinColumn(name = "idVideojuego", referencedColumnName = "idVideojuego")
    @ManyToOne(optional = false)
    private Videojuegos idVideojuego;

    public Reseñas() {
    }

    public Reseñas(Integer idReseña) {
        this.idReseña = idReseña;
    }

    public Reseñas(Integer idReseña, BigDecimal puntuacion) {
        this.idReseña = idReseña;
        this.puntuacion = puntuacion;
    }

    public Integer getIdReseña() {
        return idReseña;
    }

    public void setIdReseña(Integer idReseña) {
        this.idReseña = idReseña;
    }

    public BigDecimal getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(BigDecimal puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaReseña() {
        return fechaReseña;
    }

    public void setFechaReseña(Date fechaReseña) {
        this.fechaReseña = fechaReseña;
    }

    public Integer getUtiles() {
        return utiles;
    }

    public void setUtiles(Integer utiles) {
        this.utiles = utiles;
    }

    public Boolean getSpoilers() {
        return spoilers;
    }

    public void setSpoilers(Boolean spoilers) {
        this.spoilers = spoilers;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Videojuegos getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Videojuegos idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReseña != null ? idReseña.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reseñas)) {
            return false;
        }
        Reseñas other = (Reseñas) object;
        if ((this.idReseña == null && other.idReseña != null) || (this.idReseña != null && !this.idReseña.equals(other.idReseña))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "daw.Rese\u00f1as[ idRese\u00f1a=" + idReseña + " ]";
    }
    
}
