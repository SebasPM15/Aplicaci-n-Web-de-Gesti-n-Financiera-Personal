package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Cuenta;

public class CuentaDAO {
    
	private EntityManager em;

    public CuentaDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        this.em = emf.createEntityManager();
    }
    
    public Cuenta find(int id) {
        return em.find(Cuenta.class, id);
    }
    
    public List<Cuenta> findAll() {
        String jpql = "SELECT c FROM Cuenta c";
        return em.createQuery(jpql, Cuenta.class).getResultList();
    }
    
    public void actualizar(Cuenta cuenta) {
        em.getTransaction().begin();
        em.merge(cuenta);
        em.getTransaction().commit();
    }
    
    public List<Cuenta> findByUsuario(int usuarioId) {
        String jpql = "SELECT c FROM Cuenta c WHERE c.usuario.id = :usuarioId";
        TypedQuery<Cuenta> query = em.createQuery(jpql, Cuenta.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

}
