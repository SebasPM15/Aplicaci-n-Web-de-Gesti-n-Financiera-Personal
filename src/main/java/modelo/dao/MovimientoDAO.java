package modelo.dao;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Movimiento;

public class MovimientoDAO {

    protected EntityManager em;

    public MovimientoDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        this.em = emf.createEntityManager();
    }
    
    public Movimiento find(int id) {
        return em.find(Movimiento.class, id);
    }
    
    public List<Movimiento> findAll() {
        String jpql = "SELECT m FROM Movimiento m";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
    
    public List<Movimiento> findByUsuarioAndFecha(int usuarioId, Date fecha) {
        // No cierres el EntityManager aquí
        String jpql = "SELECT m FROM Movimiento m WHERE m.usuario.id = :usuarioId" +
                      (fecha != null ? " AND m.fecha = :fecha" : "");
        TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
        query.setParameter("usuarioId", usuarioId);
        
        if (fecha != null) {
            query.setParameter("fecha", fecha);
        }
        
        return query.getResultList();
    }

    public List<Movimiento> findByUsuario(int usuarioId) {
            String jpql = "SELECT m FROM Movimiento m WHERE m.usuario.id = :usuarioId";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("usuarioId", usuarioId);
            return query.getResultList();
    }
    
    //Metodos agregados para los nuevos dos casos de uso
    public void actualizar(Movimiento movimiento) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(movimiento);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminar(Movimiento movimiento) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (!em.contains(movimiento)) {
                movimiento = em.merge(movimiento);
            }
            em.remove(movimiento);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
