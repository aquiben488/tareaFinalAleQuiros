package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManager {
    private static final String PERSISTENCE_UNIT = "VideojuegosPU";
    private static EntityManagerFactory emf;
    
    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Exception e) {
            System.err.println("Error al crear EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
