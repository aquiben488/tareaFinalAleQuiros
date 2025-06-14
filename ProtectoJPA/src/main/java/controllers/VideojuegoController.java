package controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import models.Videojuego;
import models.Categoria;
import models.Usuario;
import utils.PersistenceManager;
import java.util.List;

/**
* La logica de los metodos es la siguiente:
*
* cualquier error en datos de entrada se lanza una excepcion de tipo IllegalArgumentException
* cualquier error en la base de datos se lanza una excepcion de tipo RuntimeException
*
* el entityManager se cierra siempre en el finally
* para evitar que se quede abierto o se cierre 2 veces
*
* en el caso de buscar todos se considera un error de base de datos si no hay elementos
* ya que no hay datos de entrada que puedan dar error
*
* la estrategia para controlar la integridad de los datos en actualizacion
* no es completamente robusta (habria que hacer varias condiciones y buscar codigo de error
* concreto) pero por simplicidad solo se busca texto en el mensaje de error
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
        } catch (IllegalArgumentException e) {
            throw e;
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
        } catch (IllegalArgumentException e) {
            throw e;
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
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizar(Videojuego videojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Categoria cat = em.find(Categoria.class, videojuego.getIdCategoria());
            if (cat == null) {
                throw new IllegalArgumentException("La categoria no existe");
            }
            em.getTransaction().begin();
            em.merge(videojuego);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
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
        } catch (IllegalArgumentException e) {
            throw e;
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
            Categoria cat = em.find(Categoria.class, categoria.getIdCategoria());
            if (cat == null) {
                throw new IllegalArgumentException("La categoria no existe");
            }
            List<Videojuego> list = em
                    .createQuery("SELECT v FROM Videojuego v WHERE v.categoria.idCategoria = :idCategoria")
                    .setParameter("idCategoria", categoria.getIdCategoria())
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (RuntimeException e) {
            throw e;
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
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Videojuego> buscarPorAño(int año) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Videojuego> list = em.createQuery("SELECT v FROM Videojuego v WHERE EXTRACT(YEAR FROM v.fechaLanzamiento) = :anyo", Videojuego.class)
                    .setParameter("anyo", año)
                    .getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ningun videojuego");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los videojuegos: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    // Metodos para la interfaz grafica
    
    public int getContadorReseñas(Integer idVideojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Verificar que el videojuego existe
            Videojuego vj = em.find(Videojuego.class, idVideojuego);
            if (vj == null) {
                throw new IllegalArgumentException("No se ha encontrado videojuego con ese Id");
            }
            
            Integer count = em.createQuery("SELECT COUNT(r) FROM Reseña r WHERE r.videojuego.idVideojuego = :idVideojuego", Integer.class)
                    .setParameter("idVideojuego", idVideojuego)
                    .getSingleResult();
            
            return count;
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return 0;
        }
        catch (Exception e) {
            throw new RuntimeException("Error al obtener contador de reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    public double getNotaMedia(Integer idVideojuego) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Videojuego vj = em.find(Videojuego.class, idVideojuego);
            if (vj == null) {
                throw new IllegalArgumentException("No se ha encontrado videojuego con ese Id");
            }
            
            // Calcular media de puntuaciones
            Double media = em.createQuery("SELECT AVG(r.puntuacion) FROM Reseña r WHERE r.videojuego.idVideojuego = :idVideojuego", Double.class)
                    .setParameter("idVideojuego", idVideojuego)
                    .getSingleResult();
            
            // Si no hay reseñas, AVG devuelve null
            return media != null ? Math.round(media * 100.0) / 100.0 : 0.0;
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener nota media: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public String getEstadisticasTexto(Integer idVideojuego) {
        try {
            int totalReseñas = getContadorReseñas(idVideojuego);
            double notaMedia = getNotaMedia(idVideojuego);
            
            if (totalReseñas == 0) {
                return "Sin reseñas";
            }
            
            // esto es idea completa de claude
            return String.format("⭐ %.1f/10 | 📝 %d reseña%s",
                    notaMedia,
                    totalReseñas,
                    totalReseñas == 1 ? "" : "s");
                    
        } catch (Exception e) {
            return "Error al cargar estadísticas";
        }
    }
    
}
