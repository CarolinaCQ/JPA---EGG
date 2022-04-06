package libreria.servicio;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.persistencia.AutorDAO;

public class AutorServicio {

    private AutorDAO autorDAO;

    public AutorServicio() {
        this.autorDAO = new AutorDAO();
    }

    Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);

    private String insertarNombre() {
        System.out.println("Ingrese el nombre del autor: ");
        String nombre = entrada.next();
        return nombre;
    }

    private Boolean insertarAlta() {
        Boolean alta = true;
        return alta;
    }

    private void modificarAlta(Autor autor) {
        System.out.println("Desea dar la alta/baja? SI/NO");
        String opcion = entrada.next();
        if (opcion.equalsIgnoreCase("si")) {
            System.out.println("ALTA o BAJA");
            String opcionAltaBaja = entrada.next();
            if (opcionAltaBaja.equalsIgnoreCase("alta")) {
                autor.setAlta(true);
            } else {
                autor.setAlta(false);
            }
        }
    }

    public void crearAutor() throws Exception {
        Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);
        try {

            Autor autor = new Autor();

            autor.setNombre(insertarNombre());
            autor.setAlta(insertarAlta());

            agregarAutor(autor);
        } catch (Exception e) {
            throw e;
        }

    }

    private void agregarAutor(Autor autor) throws Exception {
        try {
            if (autor == null) {
                throw new Exception("No existe el autor");
            } else {
                autorDAO.crear(autor);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarAutor() throws Exception {
        try {
            System.out.println("Escoja el ID del autor a modificar: ");
            this.imprimirTotal();
            Integer id = entrada.nextInt();

            Autor autor = autorDAO.buscarPorId(id);

            System.out.println("Que desea modificar?");
            System.out.println("1) Nombre");
            System.out.println("2) Alta");
            System.out.println("3) Salir");
            
            int opcion; 
            
            do {
                opcion = entrada.nextInt();
                
                switch(opcion){
                    case 1:
                        autor.setNombre(insertarNombre());
                        break;
                    case 2:
                        modificarAlta(autor);
                        break;
                    case 3:
                        System.out.println("Ha salido");
                        break;
                    default:
                        System.out.println("Ingrese una opción valida");
                }
                
            } while (opcion!=3);
            

            autorDAO.modificar(autor);
        } catch (Exception e) {
            throw e;
        }
    }

    public void imprimirTotal() throws Exception {
        try {
            List<Autor> autores = autorDAO.buscarTotal();
            System.out.printf("%-10s%-35s \n", "ID", "NOMBRE");
            for (Autor autor : autores) {
                System.out.printf("%-10s%-35s \n", autor.getId(), autor.getNombre());
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private Integer ingresarIdABuscar() {
        System.out.println("Ingrese el ID del autor que desea buscar: ");
        Integer id = entrada.nextInt();
        return id;

    }

    public void obtenerAutorPorId() throws Exception {
        try {
            Integer id = ingresarIdABuscar();

            Autor autor = autorDAO.buscarPorId(id);

            if (autor == null) {
                throw new Exception("El autor no existe");
            } else {
                System.out.printf("%-10s%-20s \n", "ID", "NOMBRE");
                System.out.printf("%-10s%-20s \n", autor.getId(), autor.getNombre());
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void elminarAutor() throws Exception {
        try {
            
            this.imprimirTotal();
            
            Integer id = ingresarIdABuscar();

            Autor autor = autorDAO.buscarPorId(id);

            if (autor == null) {
                throw new Exception("No existe el autor");
            } else {
                autor.setAlta(false);
                autorDAO.eliminar(autor);
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
