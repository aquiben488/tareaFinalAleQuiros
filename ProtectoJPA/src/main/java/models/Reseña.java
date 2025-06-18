/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;

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
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "resenas") // antes reseñas pero la ñ da problemas
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reseña.findAll", query = "SELECT r FROM Reseña r"),
    @NamedQuery(name = "Reseña.findByIdReseña", query = "SELECT r FROM Reseña r WHERE r.idReseña = :idReseña"),
    @NamedQuery(name = "Reseña.findByPuntuacion", query = "SELECT r FROM Reseña r WHERE r.puntuacion = :puntuacion"),
    @NamedQuery(name = "Reseña.findByFechaReseña", query = "SELECT r FROM Reseña r WHERE r.fechaReseña = :fechaReseña"),
    @NamedQuery(name = "Reseña.findByUtiles", query = "SELECT r FROM Reseña r WHERE r.utiles = :utiles"),
    @NamedQuery(name = "Reseña.findBySpoilers", query = "SELECT r FROM Reseña r WHERE r.spoilers = :spoilers"),
    @NamedQuery(name = "Reseña.findByUsuario", query = "SELECT r FROM Reseña r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "Reseña.findByVideojuego", query = "SELECT r FROM Reseña r WHERE r.videojuego = :videojuego"),
    @NamedQuery(name = "Reseña.findByUsuarioVideojuego", query = "SELECT r FROM Reseña r WHERE r.usuario = :usuario AND r.videojuego = :videojuego"),
    @NamedQuery(name = "Reseña.findByPuntuacionMinima", query = "SELECT r FROM Reseña r WHERE r.puntuacion >= :puntuacionMinima")
})
public class Reseña implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idResena")
    private Integer idReseña;
    @Basic(optional = false)
    @Column(name = "puntuacion")
    private Double puntuacion;
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "fechaResena")
    private LocalDate fechaReseña;
    @Column(name = "utiles")
    private Integer utiles;
    @Column(name = "spoilers")
    private Boolean spoilers;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "idVideojuego", referencedColumnName = "idVideojuego")
    @ManyToOne(optional = false)
    private Videojuego videojuego;

    public Reseña() {
    }

    public Reseña(Integer idReseña) {
        this.idReseña = idReseña;
    }

    public Reseña(Integer idReseña, Double puntuacion) {
        this.idReseña = idReseña;
        this.puntuacion = puntuacion;
    }

    public Reseña(String puntuacion, String comentario, Boolean spoilers, Usuario usuario, Videojuego videojuego) {
        try {
            this.puntuacion = Double.valueOf(puntuacion);
            if (this.puntuacion < 0 || this.puntuacion > 10) {
                throw new IllegalArgumentException("La puntuación debe estar entre 0 y 10");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La puntuación debe ser un número válido");
        }
        this.comentario = comentario;
        this.spoilers = spoilers;
        this.usuario = usuario;
        this.videojuego = videojuego;
        this.fechaReseña = LocalDate.now();
        this.utiles = 0;
    }

    public Integer getIdReseña() {
        return idReseña;
    }

    public void setIdReseña(Integer idReseña) {
        this.idReseña = idReseña;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFechaReseña() {
        return fechaReseña;
    }

    public void setFechaReseña(LocalDate fechaReseña) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    /**
     * Métodos de compatibilidad para que el código que aún usa getIdUsuario()/getIdVideojuego()
     * siga compilando. Devuelven la ENTIDAD asociada, tal y como esperan actualmente
     * los controladores.
     */
    public Usuario getIdUsuario() {
        return usuario;
    }

    public void setIdUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Videojuego getIdVideojuego() {
        return videojuego;
    }

    public void setIdVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
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
        if (!(object instanceof Reseña)) {
            return false;
        }
        Reseña other = (Reseña) object;
        if ((this.idReseña == null && other.idReseña != null) || (this.idReseña != null && !this.idReseña.equals(other.idReseña))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        try {
            controllers.ReseñaController controller = new controllers.ReseñaController();
            return controller.getEstadisticasReseña(this.idReseña);
        } catch (Exception e) {
            // Fallback si hay error
            String usuario = (this.usuario != null) ? this.usuario.getNombre() : "Usuario desconocido";
            String videojuego = (this.videojuego != null) ? this.videojuego.getTitulo() : "Videojuego desconocido";
            return usuario + " - " + videojuego + " | Sin datos disponibles";
        }
    }
    
}
