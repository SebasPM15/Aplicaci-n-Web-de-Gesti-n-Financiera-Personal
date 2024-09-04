package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Usuario;

public class UsuarioDAO {

	EntityManager em = null;
	
	public UsuarioDAO() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
		this.em = emf.createEntityManager();
	}
	
    public Usuario autenticar(String nombre, String clave) {
        //CON ORM
        String sentenciaJPQL = "SELECT u FROM Usuario u WHERE u.nombre= :nombre AND u.clave= :clave";
        Query consulta = em.createQuery(sentenciaJPQL);
        consulta.setParameter("nombre", nombre);
        consulta.setParameter("clave", clave);
        
        Usuario usuario = null;
        try {
            usuario = (Usuario) consulta.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
