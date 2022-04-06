package libreria.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Autor;

public class AutorDAO {

    private final EntityManager em = Persistence
            .createEntityManagerFactory("LibreriaJPAPU")
            .createEntityManager();

    
    public void crear(Autor autor) throws Exception {
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al crear autor");
        }
    }

    
    public void modificar(Autor autor) throws Exception {
        try {
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Eror al modificar autor");
        }
    }

    
    public void eliminar(Autor autor) throws Exception {
        try {
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al eliminar autor");
        }
    }

    
    public  Autor buscarPorId(Integer id) throws Exception {
        try {
            Autor autor = em.find(Autor.class, id);
            return autor;
        } catch (Exception e) {
            throw new Exception("Eror al buscar por id");
        }
    }

    
    public List<Autor> buscarTotal() throws Exception {
        try {
            List<Autor> autores = em.createQuery("SELECT a FROM Autor a", Autor.class)
                    .getResultList();

            return autores;
        } catch (Exception e) {
            throw new Exception("Eror al buscar total de autores");
        }
    }

    
    public List<Autor> buscarPorNombre(String nombre) throws Exception {
        List<Autor> autores;
        try {
            em.getTransaction().begin();
            
            autores = em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :name", Autor.class)
                    .setParameter("name", nombre)
                    .getResultList();
                    
            em.getTransaction().commit();
            return autores;
        } catch (Exception ex) {
            throw new Exception("Error al buscar por nombre");
        }
    }

}
