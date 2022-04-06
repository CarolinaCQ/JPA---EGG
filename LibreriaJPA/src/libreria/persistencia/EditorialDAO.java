package libreria.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Editorial;

public class EditorialDAO {

    private final EntityManager em = Persistence
            .createEntityManagerFactory("LibreriaJPAPU")
            .createEntityManager();

    
    public void crear(Editorial editorial) throws Exception {
        try {
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al crear editorial");
        }
    }

   
    public void modificar(Editorial editorial) throws Exception {
        try {
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al modificar editorial");
        }
    }

    
    public void eliminar(Editorial editorial) throws Exception {
        try {
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al eliminar editorial");
        }
    }

    
    public Editorial buscarPorId(Integer id) throws Exception {
        try {
            Editorial editorial = em.find(Editorial.class, id);
            return editorial;
        } catch (Exception e) {
            throw new Exception("Error al buscar por id");
        }
    }

    
    public List<Editorial> buscarPorNombre(String nombre) throws Exception {
        try {
            List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :name", Editorial.class)
                    .setParameter("name", nombre)
                    .getResultList();

            return editoriales;
        } catch (Exception ex) {
            throw new Exception("Error al buscar por nombre");
        }
    }

   
    public List<Editorial> buscarTotal() throws Exception {
        try {
            List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e", Editorial.class)
                    .getResultList();

            return editoriales;
        } catch (Exception e) {
            throw new Exception("Error al buscar el total de editoriales");
        }
    }
}

