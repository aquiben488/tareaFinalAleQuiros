package controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import models.Videojuego;
import models.Categoria;
import utils.PersistenceManager;
import java.util.List;

/**
 * Controlador CRUD para la entidad Videojuego
 * 
 * CONSIDERACIONES ESPECIALES:
 * - Videojuego tiene relación ManyToOne con Categoria
 * - Videojuego tiene relación OneToMany con Reseña
 * - Incluir métodos de búsqueda por categoría y fecha
 */
public class VideojuegoController {

    public void crear(Videojuego videojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Categoria cat = em.find(Categoria.class, videojuego.getIdCategoria());
            if (cat == null) {
                throw new IllegalArgumentException("La categoria no existe");
            }
            em.getTransaction().begin();
            em.persist(videojuego);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("El videojuego ya existe");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("La categoria no existe")) {
                throw new IllegalArgumentException("La categoria no existe");
            }
            throw new RuntimeException("No se ha podido crear el videojuego");
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Videojuego buscarPorId(Integer id) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Videojuego vj = em.find(Videojuego.class, id);
            if (vj != null) {
                return vj;
            }
            throw new IllegalArgumentException("No se ha encontrado videojuego con ese Id");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar el videojuego: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Videojuego> buscarTodos() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Videojuego> list = em.createNamedQuery("Videojuego.findAll", Videojuego.class)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizar(Videojuego videojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        CategoriaController catCon = new CategoriaController();
        try {
            catCon.buscarPorId(videojuego.getIdCategoria());
            em.getTransaction().begin();
            em.merge(videojuego);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La categoria no existe");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al actualizar el videojuego: " + e.getMessage());
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
            Videojuego vj = em.find(Videojuego.class, id);
            if (vj == null) {
                throw new IllegalArgumentException("No se ha encontrado videojuego con ese Id");
            }
            em.getTransaction().begin();
            em.remove(vj);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("No se ha podido eliminar el videojuego");
        } finally {
            em.close();
        }
    }

    public List<Videojuego> buscarPorCategoria(Categoria categoria) {
        EntityManager em = PersistenceManager.getEntityManager();

        try {
            CategoriaController catCon = new CategoriaController();
            catCon.buscarPorId(categoria.getIdCategoria());
            List<Videojuego> list = em
                    .createQuery("SELECT v FROM Videojuego v WHERE v.categoria.idCategoria = :idCategoria")
                    .setParameter("idCategoria", categoria.getIdCategoria())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La categoria no existe");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Videojuego> buscarPorTitulo(String titulo) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Videojuego> list = em.createQuery("SELECT v FROM Videojuego v WHERE v.titulo LIKE :titulo")
                    .setParameter("titulo", "%" + titulo + "%")
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Videojuego> buscarPorAño(int año) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Videojuego> list = em.createQuery("SELECT v FROM Videojuego v WHERE YEAR(v.fechaLanzamiento) = :año")
                    .setParameter("año", año)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
