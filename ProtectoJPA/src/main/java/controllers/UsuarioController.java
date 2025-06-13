package controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Usuario;
import utils.PersistenceManager;
import java.util.List;

public class UsuarioController {

    public void crear(Usuario usuario) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("El usuario ya existe");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("El usuario ya existe")) {
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
            // ReseñaController.moverReseñasAEliminadas(usu);
            // movemos todas las reseñas del usuario a usuario "Eliminado"
            // TODO: Implementar moverReseñasAEliminadas
            em.remove(usu);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("No se ha podido eliminar el usuario");
        } finally {
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

    public boolean validarLogin(String email, String contraseña) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            String contraseñaReal = em.createNamedQuery("Usuario.findContraseñaByEmail", String.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return contraseña.equals(contraseñaReal);
        } catch (NoResultException e) {
            throw new IllegalArgumentException("El email no esta registrado en la base de datos");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
