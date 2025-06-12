package controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import models.Categoria;
import utils.PersistenceManager;
import java.util.List;

public class CategoriaController {

    public void crear(Categoria categoria) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("La categoria ya existe");
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear la categoria");
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
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar la categoria: " + e.getMessage());
        }
        finally {
            em.close();
        }
    }

    public List<Categoria> buscarTodas() {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Categoria> list = em.createNamedQuery("Categoria.findAll").getResultList();
            if (!list.isEmpty()) {
                return list;
            }
            throw new RuntimeException("No existe ninguna categoria");
        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al buscar las categorias: " + e.getMessage());
        }
        finally {
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
}
