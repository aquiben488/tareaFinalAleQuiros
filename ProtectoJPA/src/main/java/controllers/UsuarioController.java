package controllers;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import models.Usuario;
import utils.PersistenceManager;

public class UsuarioController {

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

    public void crear(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("El usuario ya existe");
        } catch (Exception e) {
            // Detectar email duplicado (patón KISS)
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("email")) {
                    throw new IllegalArgumentException("Ya existe un usuario con ese email");
                }
                throw new IllegalArgumentException("El usuario ya existe");
            }
            throw new RuntimeException("No se ha podido crear el usuario");
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Usuario buscarPorId(Integer id) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Usuario usu = em.find(Usuario.class, id);
            if (usu != null) {
                return usu;
            }
            throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Usuario> buscarTodos() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Usuario> list = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No se ha encontrado ningun usuario registrado");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar los usuarios: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void actualizar(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Usuario usu = em.find(Usuario.class, usuario.getIdUsuario());
            if (usu == null || usu.getIdUsuario() == 1) {
                throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
            }
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage());
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
            Usuario usu = em.find(Usuario.class, id);
            if (usu == null || usu.getIdUsuario() == 1) {
                // si el usuario no existe o es el usuario Eliminado, se lanza una excepcion
                throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
            }

            em.getTransaction().begin();
            ReseñaController.moverReseñasAEliminadas(usu);
            em.remove(usu);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido eliminar el usuario: " + e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public Usuario buscarPorEmail(String email) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            Usuario usu = em.createNamedQuery("Usuario.findByEmail", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return usu;
        } catch (NoResultException e) {
            throw new IllegalArgumentException("No se ha encontrado usuario con ese email");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Usuario validarLogin(String email, String contraseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            String contraseñaReal = em.createNamedQuery("Usuario.findContraseñaByEmail", String.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (contraseña.equals(contraseñaReal)) {
                return buscarPorEmail(email);
            }
            throw new IllegalArgumentException("La contraseña es incorrecta");
        } catch (NoResultException e) {
            throw new IllegalArgumentException("El email no esta registrado en la base de datos");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    // Metodos para la interfaz grafica
 
    public int getContadorReseñasEscritas(Integer idUsuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Verificar que el usuario existe
            Usuario user = em.find(Usuario.class, idUsuario);
            if (user == null) {
                throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
            }
            
            // Contar reseñas del usuario
            Long count = em.createQuery("SELECT COUNT(r) FROM Reseña r WHERE r.usuario.idUsuario = :idUsuario", Long.class)
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
            
            return count.intValue();
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener contador de reseñas: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    public double getPromedioCalificacionesDadas(Integer idUsuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Verificar que el usuario existe
            Usuario user = em.find(Usuario.class, idUsuario);
            if (user == null) {
                throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
            }
            
            // Calcular promedio de puntuaciones que da el usuario
            Double promedio = em.createQuery("SELECT AVG(r.puntuacion) FROM Reseña r WHERE r.usuario.idUsuario = :idUsuario", Double.class)
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
            
            // Si no ha escrito reseñas, AVG devuelve null
            return promedio != null ? Math.round(promedio * 100.0) / 100.0 : 0.0;
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener promedio de calificaciones: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    public int getTotalUpvotesRecibidos(Integer idUsuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            // Verificar que el usuario existe
            Usuario user = em.find(Usuario.class, idUsuario);
            if (user == null) {
                throw new IllegalArgumentException("No se ha encontrado usuario con ese Id");
            }
            
            // Sumar todos los upvotes de las reseñas del usuario
            Integer totalUpvotes = em.createQuery("SELECT SUM(r.utiles) FROM Reseña r WHERE r.usuario.idUsuario = :idUsuario", Integer.class)
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
            
            return totalUpvotes != null ? totalUpvotes : 0;
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NoResultException e) {
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener total de upvotes: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public String getEstadisticasUsuario(Integer idUsuario) {
        try {
            int totalReseñas = getContadorReseñasEscritas(idUsuario);
            double promedioCalificaciones = getPromedioCalificacionesDadas(idUsuario);
            int totalUpvotes = getTotalUpvotesRecibidos(idUsuario);
            
            if (totalReseñas == 0) {
                return "Sin reseñas escritas";
            }
            
            return String.format("📝 %d reseña%s | ⭐ Promedio: %.1f | 👍 %d útil%s", 
                    totalReseñas,
                    totalReseñas == 1 ? "" : "s",
                    promedioCalificaciones,
                    totalUpvotes,
                    totalUpvotes == 1 ? "" : "es");
                    
        } catch (Exception e) {
            return "Error al cargar estadísticas";
        }
    }
    
}
