package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import modelo.entidades.Transferencia;

public class TransferenciaDAO extends MovimientoDAO {
    
	public TransferenciaDAO() {
        super();
    }

    @Override
    public Transferencia find(int id) {
        return em.find(Transferencia.class, id);
    }
    
    public void guardar(Transferencia transferencia) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(transferencia);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Transferencia> obtenerTodos() {
        String jpql = "SELECT i FROM Transferencia i";
        Query query = em.createQuery(jpql, Transferencia.class);
        return query.getResultList();
    }
}
