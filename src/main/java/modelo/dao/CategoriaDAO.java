package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Categoria;

public class CategoriaDAO {

    private EntityManager em;

    public CategoriaDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        this.em = emf.createEntityManager();
    }
    
    public Categoria find(int id) {
        return em.find(Categoria.class, id);
    }

    public void guardar(Categoria categoria) {
        em.getTransaction().begin();
        em.persist(categoria);
        em.getTransaction().commit();
    }

    public void actualizar(Categoria categoria) {
        em.getTransaction().begin();
        em.merge(categoria);
        em.getTransaction().commit();
    }
    
    public List<Categoria> findAll() {
        String jpql = "SELECT c FROM Categoria c";
        return em.createQuery(jpql, Categoria.class).getResultList();
    }

    public double calcularSaldoPorCategoria(int categoriaId) {
        String jpqlIngresos = "SELECT SUM(i.monto) FROM Ingreso i WHERE i.categoria.id_cat = :categoriaId";
        String jpqlEgresos = "SELECT SUM(e.monto) FROM Egreso e WHERE e.categoria.id_cat = :categoriaId";
        
        Query queryIngresos = em.createQuery(jpqlIngresos);
        queryIngresos.setParameter("categoriaId", categoriaId);
        
        Query queryEgresos = em.createQuery(jpqlEgresos);
        queryEgresos.setParameter("categoriaId", categoriaId);

        Double totalIngresos = (Double) queryIngresos.getSingleResult();
        Double totalEgresos = (Double) queryEgresos.getSingleResult();
        
        if (totalIngresos == null) totalIngresos = 0.0;
        if (totalEgresos == null) totalEgresos = 0.0;
        
        return totalIngresos - totalEgresos;
    }
}
