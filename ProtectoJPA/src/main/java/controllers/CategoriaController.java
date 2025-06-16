package controllers;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import models.Categoria;
import utils.PersistenceManager;


public class CategoriaController {
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

    public void crear(Categoria categoria) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("La categoria ya existe");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                throw new IllegalArgumentException("ya existe una categoria con ese nombre");
            }
            throw new RuntimeException("No se ha podido crear la categoria" + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Categoria buscarPorId(Integer id) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Categoria cat = em.find(Categoria.class, id);
            if (cat != null) {
                return cat;
            }
            throw new IllegalArgumentException("No se ha encontrado categoria con ese Id");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar la categoria: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Categoria> buscarTodas() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Categoria> list = em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna categoria");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las categorias: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizar(Categoria categoria) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido actualizar la categoria: " + e.getMessage());
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
            em.getTransaction().begin();
            Categoria cat = em.find(Categoria.class, id);
            if (cat == null) {
                throw new IllegalArgumentException("No se ha encontrado categoria con ese Id");
            }
            em.remove(cat);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido eliminar la categoria: " + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Categoria buscarPorNombre(String nombre) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Categoria cat = em.createNamedQuery("Categoria.findByNombre", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            return cat;
        } catch (NoResultException e) {
            throw new IllegalArgumentException("No se ha encontrado categoria con ese nombre");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar la categoria: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    // Metodos para la interfaz grafica

    public String getEstadisticasCategoria(Integer idCategoria) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Verificar que la categoría existe
            Categoria cat = em.find(Categoria.class, idCategoria);
            if (cat == null) {
                throw new IllegalArgumentException("La categoria no existe");
            }
            
            // Contar videojuegos en la categoría (COUNT devuelve Long)
            Long totalJuegosLong = em.createQuery("SELECT COUNT(v) FROM Videojuego v WHERE v.categoria.idCategoria = :idCategoria", Long.class)
                    .setParameter("idCategoria", idCategoria)
                    .getSingleResult();
            
            // Contar reseñas total de la categoría (COUNT devuelve Long)
            Long totalReseñasLong = em.createQuery("SELECT COUNT(r) FROM Reseña r WHERE r.videojuego.categoria.idCategoria = :idCategoria", Long.class)
                    .setParameter("idCategoria", idCategoria)
                    .getSingleResult();
            
            // Convertir a int para el formato
            int totalJuegos = totalJuegosLong.intValue();
            int totalReseñas = totalReseñasLong.intValue();
            
            return String.format("🎮 %d juego%s | 📝 %d reseña%s",
                    totalJuegos,
                    totalJuegos == 1 ? "" : "s",
                    totalReseñas,
                    totalReseñas == 1 ? "" : "s");
                    
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return "Sin videojuegos";
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener estadísticas de categoría: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
