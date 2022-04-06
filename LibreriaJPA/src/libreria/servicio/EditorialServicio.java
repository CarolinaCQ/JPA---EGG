package libreria.servicio;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import libreria.entidades.Editorial;
import libreria.persistencia.EditorialDAO;

public class EditorialServicio {

    private EditorialDAO editorialDAO;

    public EditorialServicio() {
        this.editorialDAO = new EditorialDAO();
    }

    Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);

    private String insertarNombre() {
        System.out.println("Ingrese el nombre de la editorial: ");
        String nombre = entrada.next();
        return nombre;
    }

    private Boolean insertarAlta() {
        Boolean alta = true;
        return alta;
    }

    private void modificarAlta(Editorial editorial) {
        System.out.println("Ingrese el nombre de la editorial: ");
        String nombre = entrada.next();
        editorial.setNombre(nombre);
        System.out.println("Desea dar la alta/baja? SI/NO");
        String opcion = entrada.next();
        if (opcion.equalsIgnoreCase("si")) {
            System.out.println("ALTA o BAJA");
            String opcionAltaBaja = entrada.next();
            if (opcionAltaBaja.equalsIgnoreCase("alta")) {
                editorial.setAlta(true);
            } else {
                editorial.setAlta(false);
            }
        }
    }

    public void crearEditorial() throws Exception {

        Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);
        try {

            Editorial editorial = new Editorial();

            editorial.setNombre(insertarNombre());
            editorial.setAlta(insertarAlta());

            agregarEditorial(editorial);
        } catch (Exception e) {
            throw e;
        }

    }

    private void agregarEditorial(Editorial editorial) throws Exception {
        try {
            editorialDAO.crear(editorial);
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarEditorial() throws Exception {
        try {
            System.out.println("Escoja el ID de la editorial a modificar: ");
            this.imprimirTotal();
            Integer id = entrada.nextInt();

            Editorial editorial = editorialDAO.buscarPorId(id);
            
            System.out.println("Que desea modificar?");
            System.out.println("1) Nombre");
            System.out.println("2) Alta");
            System.out.println("3) Salir");
            
            int opcion; 
            
            do {
                opcion = entrada.nextInt();
                
                switch(opcion){
                    case 1:
                        editorial.setNombre(insertarNombre());
                        break;
                    case 2:
                        modificarAlta(editorial);
                        break;
                    case 3:
                        System.out.println("Ha salido");
                        break;
                    default:
                        System.out.println("Ingrese una opción valida");
                }
                
            } while (opcion!=3);

            editorialDAO.modificar(editorial);
        } catch (Exception e) {
            throw e;
        }

    }

    public void imprimirTotal() throws Exception {
        try {

            List<Editorial> editoriales = editorialDAO.buscarTotal();

            System.out.printf("%-10s%-35s \n", "ID", "NOMBRE");
            for (Editorial editorial : editoriales) {
                System.out.printf("%-10s%-35s \n", editorial.getId(), editorial.getNombre());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private Integer ingresarIdABuscar() {
        System.out.println("Ingrese el ID de la editorial que desea buscar: ");
        Integer id = entrada.nextInt();
        return id;

    }

    public void obtenerEditorialPorId() throws Exception {
        try {
            Integer id = ingresarIdABuscar();

            Editorial editorial = editorialDAO.buscarPorId(id);

            if (editorial == null) {
                throw new Exception("La editorial no existe");
            } else {
                System.out.printf("%-10s%-20s \n", "ID", "NOMBRE");
                System.out.printf("%-10s%-20s \n", editorial.getId(), editorial.getNombre());
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void elminarEditorial() throws Exception {
        try {
            this.imprimirTotal();
            
            Integer id = ingresarIdABuscar();

            Editorial editorial = editorialDAO.buscarPorId(id);

            if (editorial == null) {
                throw new Exception("No existe el autor");
            } else {
                editorial.setAlta(false);
                editorialDAO.eliminar(editorial);
            }

        } catch (Exception e) {
            throw e;

        }
    }
}
