package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import modelo.entidades.Egreso;

public class EgresoDAO extends MovimientoDAO{
	
    public EgresoDAO() {
        super();
    }

    @Override
    public Egreso find(int id) {
        return em.find(Egreso.class, id);
    }
    
    public void guardar(Egreso egreso) {
        EntityTransaction tx = em.getTransaction();
        try {
			egreso.setMonto(-Math.abs(egreso.getMonto()));
        	tx.begin();
			em.persist(egreso);
			tx.commit();
		} catch (Exception e) {
			if(tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
    }
    
    public List<Egreso> obtenerTodos() {
        String jpql = "SELECT i FROM Egreso i";
        Query query = em.createQuery(jpql, Egreso.class);
        return query.getResultList();
    }
}
