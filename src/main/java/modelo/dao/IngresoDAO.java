package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import modelo.entidades.Ingreso;

public class IngresoDAO extends MovimientoDAO{
    
	public IngresoDAO() {
        super();
    }

    @Override
    public Ingreso find(int id) {
        return em.find(Ingreso.class, id);
    }
    
    public void guardar(Ingreso ingreso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(ingreso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Ingreso> obtenerTodos() {
        String jpql = "SELECT i FROM Ingreso i";
        Query query = em.createQuery(jpql, Ingreso.class);
        return query.getResultList();
    }
}
