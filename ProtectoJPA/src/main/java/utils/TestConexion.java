package utils;

import models.Categoria;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TestConexion {
    public static void main(String[] args) {
        EntityManager em = PersistenceManager.getEntityManager();
        try {
            List<Categoria> categorias = em.createQuery("SELECT c FROM Categoria c", Categoria.class)
                                           .getResultList();
            System.out.println("✅ Conexión exitosa! Categorías encontradas: " + categorias.size());
            
            for (Categoria cat : categorias) {
                System.out.println("- " + cat.getNombre());
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            PersistenceManager.closeEntityManagerFactory();
        }
    }
}