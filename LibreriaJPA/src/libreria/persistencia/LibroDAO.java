package libreria.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Libro;

public class LibroDAO {

    private final EntityManager em = Persistence
            .createEntityManagerFactory("LibreriaJPAPU") 
            .createEntityManager();

    public void crear(Libro libro) throws Exception {
        try {
            em.getTransaction().begin(); 
            em.persist(libro);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al agregar libro");
        }
    }


    public void modificar(Libro libro) throws Exception {
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al modificar libro");
        }
    }

    
    public void eliminar(Libro libro) throws Exception {
        try {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al elimnar libro");
        }
    }

    
    public Libro buscarPorId(Long isbn) throws Exception {
        try {
            Libro libro = em.find(Libro.class, isbn);
            return libro;
        } catch (Exception e) {
            throw new Exception("Error al buscar por isbn");
        }
    }

    
    public List<Libro> buscarPorNombre(String nombre) throws Exception {
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :name", Libro.class)
                    .setParameter("name", nombre)
                    .getResultList();

            return libros;
        } catch (Exception ex) {
            throw new Exception("Error al buscar por nombre");
        }
    }

    public List<Libro> buscarTotal() throws Exception {
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l", Libro.class)
                    .getResultList();

            return libros;
        } catch (Exception e) {
            throw new Exception("Error al buscar el total de libros");
        }
    }
    
    public List<Libro> buscarPorAutor(Integer id) throws Exception{
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.autor.id LIKE :id", Libro.class)
                    .setParameter("id", id) 
                    .getResultList();
            
            return libros;
        } catch (Exception e) {
            throw new Exception("Error al buscar por autor");
        }
    }
    
    public List<Libro> buscarPorEditorial(Integer id) throws Exception{
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.editorial.id LIKE :id", Libro.class) 
                    .setParameter("id", id) 
                    .getResultList();
            
            return libros;
        } catch (Exception e) {
            throw new Exception("Error al buscar por editorial");
        }
    }

}
