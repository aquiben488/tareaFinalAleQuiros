package controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Reseña;
import models.Usuario;
import models.Videojuego;
import utils.PersistenceManager;
import java.util.List;
import java.math.BigDecimal;

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
            String mensaje = "";
            if (!comprobarIntegridad(em, reseña, mensaje)) {
                throw new IllegalArgumentException(mensaje); // si no se cumple, se lanza una excepcion
            }
            List<Reseña> list = em.createNamedQuery("Reseña.findByUsuarioVideojuego", Reseña.class)
                    .setParameter("usuario", reseña.getIdUsuario())
                    .setParameter("videojuego", reseña.getIdVideojuego())
                    .getResultList();
            if (!list.isEmpty()) {
                throw new IllegalArgumentException("Ya existe una reseña para ese videojuego de ese usuario");
            }
            em.getTransaction().begin();
            em.persist(reseña);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("La reseña ya existe en la base de datos");
        } catch (Exception e) {
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se ha podido eliminar la reseña");
        } finally {
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
                    .createQuery("SELECT r FROM Reseña r WHERE r.idVideojuego.idVideojuego = :idVideojuego",
                            Reseña.class)
                    .setParameter("idVideojuego", videojuego.getIdVideojuego())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña para ese videojuego");
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
                    .createQuery("SELECT r FROM Reseña r WHERE r.idUsuario.idUsuario = :idUsuario", Reseña.class)
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna reseña para ese usuario");
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
            throw new IllegalArgumentException("No existe ninguna reseña con puntuación igual o superior a " + puntuacionMinima);
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se ha podido marcar la reseña como útil");
        } finally {
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
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void moverReseñasAEliminadas(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Reseña> list = em.createQuery("SELECT r FROM Reseña r WHERE r.idUsuario = :idUsuario", Reseña.class)
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getResultList();
            for (Reseña res : list) {
                res.setUsuario(em.find(Usuario.class, 1));
                em.merge(res);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al mover las reseñas del usuario " + usuario.getNombre() + " a eliminadas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private boolean comprobarIntegridad(EntityManager em, Reseña reseña, String mensaje) {
        Usuario usu = em.find(Usuario.class, reseña.getIdUsuario());
        Videojuego vj = em.find(Videojuego.class, reseña.getIdVideojuego());
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