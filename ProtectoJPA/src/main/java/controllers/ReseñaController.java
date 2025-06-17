package controllers;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import models.Reseña;
import models.Usuario;
import models.Videojuego;
import utils.PersistenceManager;

/**
 * La logica de los metodos es la siguiente:
 *
 * cualquier error en datos de entrada se lanza una excepcion de tipo
 * IllegalArgumentException
 * cualquier error en la base de datos se lanza una excepcion de tipo
 * RuntimeException
 *
 * el entityManager se cierra siempre en el finally
 * para evitar que se quede abierto o se cierre 2 veces
 *
 * en el caso de buscar todos se considera un error de base de datos si no hay
 * elementos
 * ya que no hay datos de entrada que puedan dar error
 *
 * la estrategia para controlar la integridad de los datos en actualizacion
 * no es completamente robusta (habria que hacer varias condiciones y buscar
 * codigo de error
 * concreto) pero por simplicidad solo se busca texto en el mensaje de error
 */
public class ReseñaController {

    public void crear(Reseña reseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {

            em.getTransaction().begin();
            em.persist(reseña);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("La reseña ya existe en la base de datos");
        } catch (Exception e) {
            // Detectar duplicado usuario-videojuego (si existe constraint UNIQUE en BD)
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                throw new IllegalArgumentException("Ya existe una reseña para ese videojuego de ese usuario");
            }
            throw new RuntimeException("Ha ocurrido un error al crear la reseña: " + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Reseña buscarPorId(Integer id) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Reseña res = em.find(Reseña.class, id);
            if (res == null) {
                throw new IllegalArgumentException("No se ha encontrado la reseña con ese Id");
            }
            return res;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar la reseña: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Reseña> buscarTodas() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em.createNamedQuery("Reseña.findAll", Reseña.class).getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña registrada");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizar(Reseña reseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            String mensaje = "";
            if (!comprobarIntegridad(em, reseña, mensaje)) {
                throw new IllegalArgumentException(mensaje); // si no se cumple, se lanza una excepcion
            }
            em.getTransaction().begin();
            em.merge(reseña);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al actualizar la reseña: " + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public void eliminar(Integer id) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Reseña res = em.find(Reseña.class, id);
            if (res == null) {
                throw new IllegalArgumentException("No se ha encontrado la reseña con ese Id");
            }
            em.getTransaction().begin();
            em.remove(res);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido eliminar la reseña");
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    /**
     * Buscar reseñas de un videojuego específico
     */
    public List<Reseña> buscarPorVideojuego(Videojuego videojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em
                    .createQuery("SELECT r FROM Reseña r WHERE r.videojuego.idVideojuego = :idVideojuego",
                            Reseña.class)
                    .setParameter("idVideojuego", videojuego.getIdVideojuego())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña para ese videojuego");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Buscar reseñas de un usuario específico
     */
    public List<Reseña> buscarPorUsuario(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em
                    .createQuery("SELECT r FROM Reseña r WHERE r.usuario.idUsuario = :idUsuario", Reseña.class)
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña para ese usuario");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Filtrar reseñas por puntuación mínima
     */
    public List<Reseña> buscarPorPuntuacionMinima(double puntuacionMinima) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em.createQuery("SELECT r FROM Reseña r WHERE r.puntuacion >= :puntuacion", Reseña.class)
                    .setParameter("puntuacion", puntuacionMinima)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new IllegalArgumentException(
                    "No existe ninguna reseña con puntuación igual o superior a " + puntuacionMinima);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void marcarComoUtil(Integer idReseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Reseña res = em.find(Reseña.class, idReseña);
            if (res == null) {
                throw new IllegalArgumentException("No se ha encontrado la reseña con ese Id");
            }
            em.getTransaction().begin();
            res.setUtiles(res.getUtiles() == null ? 1 : res.getUtiles() + 1);
            em.merge(res);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido marcar la reseña como útil");
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public List<Reseña> buscarOrdenadasPorFecha() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em.createQuery("SELECT r FROM Reseña r ORDER BY r.fechaReseña DESC", Reseña.class)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Reseña> buscarOrdenadasPorUtilidad() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em.createQuery("SELECT r FROM Reseña r ORDER BY r.utiles DESC", Reseña.class)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void moverReseñasAEliminadas(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Reseña> list = em
                    .createQuery("SELECT r FROM Reseña r WHERE r.usuario.idUsuario = :idUsuario", Reseña.class)
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getResultList();
            for (Reseña res : list) {
                res.setUsuario(em.find(Usuario.class, 1));
                em.merge(res);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al mover las reseñas del usuario " + usuario.getNombre()
                    + " a eliminadas: " + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    /**
     * Método para generar estadísticas de una reseña específica
     * Formato: Usuario - Videojuego | ⭐ X.X | 👍 X útiles | 📅 fecha
     */
    public String getEstadisticasReseña(Integer idReseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Buscar la reseña
            Reseña reseña = em.find(Reseña.class, idReseña);
            if (reseña == null) {
                throw new IllegalArgumentException("No se ha encontrado la reseña con ese Id");
            }
            
            // Construir texto con formato
            StringBuilder texto = new StringBuilder();
            
            // Usuario - Videojuego
            texto.append(reseña.getUsuario().getNombre())
                 .append(" - ")
                 .append(reseña.getVideojuego().getTitulo());
            
            // Puntuación
            texto.append(" | ⭐ ").append(String.format("%.1f", reseña.getPuntuacion()));
            
            // Útiles
            int utiles = reseña.getUtiles() != null ? reseña.getUtiles() : 0;
            texto.append(" | 👍 ").append(utiles).append(" útil");
            if (utiles != 1) {
                texto.append("es");
            }
            
            // Fecha
            if (reseña.getFechaReseña() != null) {
                texto.append(" | 📅 ").append(reseña.getFechaReseña().toString());
            }
            
            // Indicador de spoilers
            if (reseña.getSpoilers() != null && reseña.getSpoilers()) {
                texto.append(" | ⚠️ Spoilers");
            }
            
            return texto.toString();
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener estadísticas de reseña: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private boolean comprobarIntegridad(EntityManager em, Reseña reseña, String mensaje) {
        Usuario usu = em.find(Usuario.class, reseña.getIdUsuario().getIdUsuario());
        Videojuego vj = em.find(Videojuego.class, reseña.getIdVideojuego().getIdVideojuego());
        // si alguno es null, se lanza una excepcion
        if (vj == null || usu == null) {
            if (vj == null && usu == null) {
                mensaje = "El videojuego y el usuario no existen";
                return false;
            }
            mensaje = vj == null ? "El videojuego no existe" : "El usuario no existe";
            return false;
        }
        return true;
    }

}