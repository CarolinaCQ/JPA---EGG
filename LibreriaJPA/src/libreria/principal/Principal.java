package libreria.principal;

import java.util.Locale;
import java.util.Scanner;
import libreria.servicio.AutorServicio;
import libreria.servicio.EditorialServicio;
import libreria.servicio.LibroServicio;

public class Principal {

    public static void main(String[] args) {

        LibroServicio libroServicio = new LibroServicio();
        AutorServicio autorServicio = new AutorServicio();
        EditorialServicio editorialServicio = new EditorialServicio();

        Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);

        try {

            int opcion;

            do {
                System.out.println("Escoja una opción del siguiente menú: ");
                System.out.println("------SECCIÓN LIBRO------");
                System.out.println("1) Agregar libro");
                System.out.println("2) Modificar libro");
                System.out.println("3) Eliminar libro");
                System.out.println("4) Obtener un listado del total de libros");
                System.out.println("5) Obtener un libro por su ISBN");
                System.out.println("6) Obtener un listado de libros por nombre");
                System.out.println("7) Obtener un listado de libros según autor");
                System.out.println("8) Obtener un listado de libros según editorial");
                System.out.println("------SECCIÓN AUTOR------");
                System.out.println("9) Agregar autor");
                System.out.println("10) Modificar autor");
                System.out.println("11) Eliminar autor");
                System.out.println("12) Obtener un listado del total de autores");
                System.out.println("13) Obtener un autor por su ID");
                System.out.println("------SECCIÓN EDITORIAL------");
                System.out.println("14) Agregar editorial");
                System.out.println("15) Modificar editorial");
                System.out.println("16) Eliminar editorial");
                System.out.println("17) Obtener un listado del total de editoriales");
                System.out.println("18) Obtener un editorial por su ID");

                opcion = entrada.nextInt();

                switch (opcion) {
                    case 1:
                        libroServicio.crearLibro();
                        break;
                    case 2:
                        libroServicio.modificarLibro();
                        break;
                    case 3:
                        libroServicio.eliminarLibro();
                        break;
                    case 4:
                        libroServicio.imprimirTotal();
                        break;
                    case 5:
                        libroServicio.obtenerLibroPorIsbn();
                        break;
                    case 6:
                        libroServicio.obtenerLibroPorNombre();
                        break;
                    case 7:
                        libroServicio.obtenerLibroPorAutor();
                        break;
                    case 8:
                        libroServicio.obtenerLibroPorEditorial();
                        break;
                    case 9:
                        autorServicio.crearAutor();
                        break;
                    case 10:
                        autorServicio.modificarAutor();
                        break;
                    case 11:
                        autorServicio.elminarAutor();
                        break;
                    case 12:
                        autorServicio.imprimirTotal();
                        break;
                    case 13:
                        autorServicio.obtenerAutorPorId();
                        break;
                    case 14:
                        editorialServicio.crearEditorial();
                        break;
                    case 15:
                        editorialServicio.modificarEditorial();
                        break;
                    case 16:
                        editorialServicio.elminarEditorial();
                        break;
                    case 17:
                        editorialServicio.imprimirTotal();
                        break;
                    case 18:
                        editorialServicio.obtenerEditorialPorId();
                        break;
                    case 19:
                        System.out.println("Ha salido del programa!");
                        break;
                    default:
                        System.out.println("La opción no es válida");

                }

            } while (opcion != 19);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
