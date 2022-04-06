package libreria.servicio;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import libreria.entidades.Libro;
import libreria.persistencia.AutorDAO;
import libreria.persistencia.EditorialDAO;
import libreria.persistencia.LibroDAO;

public class LibroServicio {

    private LibroDAO libroDAO;
    private AutorDAO autorDAO;
    private EditorialDAO editorialDAO;
    private AutorServicio autorServicio;
    private EditorialServicio editorialServicio;

    public LibroServicio() {
        this.libroDAO = new LibroDAO();
        this.autorDAO = new AutorDAO();
        this.editorialDAO = new EditorialDAO();
        this.autorServicio = new AutorServicio();
        this.editorialServicio = new EditorialServicio();
    }

    Scanner entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n").useLocale(Locale.US);

    private Long insertarIsbn() {
        System.out.println("ISBN");
        Long isbn = entrada.nextLong();
        return isbn;
    }

    private String insertarTitulo() {
        System.out.println("Título");
        String titulo = entrada.next();
        return titulo;
    }

    private Integer insertarAnio() {
        System.out.println("Año");
        Integer anio = entrada.nextInt();
        return anio;
    }

    private Integer insertarEjemplares() {
        System.out.println("Ejemplares");
        Integer ejemplares = entrada.nextInt();
        return ejemplares;
    }

    private Integer insertarEjemplaresPrestados() {
        System.out.println("Ejemplares Prestados");
        Integer ejemplaresPrestados = entrada.nextInt();
        return ejemplaresPrestados;
    }

    private Boolean insertarAlta() {
        Boolean alta = true;
        return alta;
    }

    private void modificarAlta(Libro libro) {
        System.out.println("Desea dar la alta/baja? SI/NO");
        String opcion = entrada.next();
        if (opcion.equalsIgnoreCase("si")) {
            System.out.println("ALTA o BAJA");
            String opcionAltaBaja = entrada.next();
            if (opcionAltaBaja.equalsIgnoreCase("alta")) {
                libro.setAlta(true);
            } else {
                libro.setAlta(false);
            }
        }
    }

    private void insertarAutor(Libro libro) throws Exception {
        try {
            System.out.println("e) Escoja un autor de la siguiente lista");
            autorServicio.imprimirTotal();
            System.out.println("Se encuentra en la lista? SI/NO(Agregar autor)");
            String opcion = entrada.next();
            if (opcion.equalsIgnoreCase("SI")) {
                int longitudArray = autorDAO.buscarTotal().size();
                System.out.println("Ingrese el número de ID: ");
                int opcionId = entrada.nextInt();
                for (int i = 1; i <= longitudArray; i++) {
                    if (opcionId == i) {
                        libro.setAutor(autorDAO.buscarPorId(opcionId));
                        break;
                    }
                }
            } else {
                autorServicio.crearAutor();
                System.out.println("Ha sido agregado a la lista, ingrese el ID para asignarlo al libro");
                autorServicio.imprimirTotal();
                int longitudArray = autorDAO.buscarTotal().size();
                int opcionId = entrada.nextInt();
                for (int i = 1; i <= longitudArray; i++) {
                    if (opcionId == i) {
                        libro.setAutor(autorDAO.buscarPorId(opcionId));
                        break;
                    }
                }

            }
        } catch (Exception e) {
            throw new Exception("Error al asignar el autor");
        }

    }

    private void insertarEditorial(Libro libro) throws Exception {

        try {
            System.out.println("f) Escoja una editorial de la siguiente lista");
            editorialServicio.imprimirTotal();
            System.out.println("Se encuentra en la lista? SI/NO(Agregar autor)");
            String opcionSiNo = entrada.next();
            if (opcionSiNo.equalsIgnoreCase("SI")) {
                int longitudArray = editorialDAO.buscarTotal().size();
                System.out.println("Ingrese el número de ID: ");
                int opcionIdEditorial = entrada.nextInt();
                for (int i = 1; i <= longitudArray; i++) {
                    if (opcionIdEditorial == i) {
                        libro.setEditorial(editorialDAO.buscarPorId(opcionIdEditorial));
                        break;
                    }
                }
            } else {
                editorialServicio.crearEditorial();
                System.out.println("Ha sido agregado a la lista, ingrese el ID para asignarlo al libro");
                editorialServicio.imprimirTotal();
                int longitudArray = editorialDAO.buscarTotal().size();
                int opcionId = entrada.nextInt();
                for (int i = 1; i <= longitudArray; i++) {
                    if (opcionId == i) {
                        libro.setEditorial(editorialDAO.buscarPorId(opcionId));
                        break;
                    }
                }

            }

        } catch (Exception e) {
            throw new Exception("Error al asignar la editorial");
        }

    }

    public void crearLibro() throws Exception {

        try {
            Libro libro = new Libro();

            System.out.println("Ingrese los datos del libro: ");

            libro.setIsbn(insertarIsbn());
            libro.setTitulo(insertarTitulo());
            libro.setAnio(insertarAnio());
            Integer ejemplares = insertarEjemplares();
            libro.setEjemplares(ejemplares);
            Integer ejemplaresPrestados = insertarEjemplaresPrestados();
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
            libro.setAlta(insertarAlta());
            insertarAutor(libro);
            insertarEditorial(libro);

            agregarLibro(libro);

        } catch (Exception e) {
            throw e;
        }

    }

    private void agregarLibro(Libro libro) throws Exception {
        try {
            libroDAO.crear(libro);
        } catch (Exception e) {
            throw e;
        }

    }

    public void imprimirTotal() throws Exception {
        try {

            List<Libro> libros = libroDAO.buscarTotal();
            System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", "ISBN", "TITULO", "AÑO", "EJEMPLARES", "EJEMPLARES PRESTADOS", "EJEMPLARES RESTANTES", "ALTA", "AUTOR", "EDITORIAL");

            for (Libro libro : libros) {
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes(), libro.getAlta(), libro.getAutor().getNombre(), libro.getEditorial().getNombre());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarLibro() throws Exception {
        try {
            System.out.println("Escoja el ISBN del libro a editar: ");
            this.imprimirTotal();
            Long isbn = entrada.nextLong();
            Libro libro = libroDAO.buscarPorId(isbn);

            if (libro == null) {
                throw new Exception("No existe el libro");
            } else {

                int opcion;

                do {
                    System.out.println("Que desea modificar?");
                    System.out.println("1) ISBN");
                    System.out.println("2) Título");
                    System.out.println("3) Año");
                    System.out.println("4) Ejemplares");
                    System.out.println("5) Ejemplares Prestados");
                    System.out.println("6) Alta");
                    System.out.println("7) Autor");
                    System.out.println("8) Editorial");
                    System.out.println("9) Salir");
                    
                    opcion = entrada.nextInt();

                    switch (opcion) {
                        case 1:
                            libro.setIsbn(insertarIsbn());
                            break;
                        case 2:
                            libro.setTitulo(insertarTitulo());
                            break;
                        case 3:
                            libro.setAnio(insertarAnio());
                            break;
                        case 4:
                            libro.setEjemplares(insertarEjemplares());
                            break;
                        case 5:
                            libro.setEjemplaresPrestados(insertarEjemplaresPrestados());
                            break;
                        case 6:
                            modificarAlta(libro);
                            break;
                        case 7:
                            insertarAutor(libro);
                            break;
                        case 8:
                            insertarEditorial(libro);
                            break;
                        case 9:
                            System.out.println("Ha salido");
                            break;
                        default:
                            System.out.println("Ingrese una opción valida");
                    }

                } while (opcion != 9);

                libroDAO.modificar(libro);

            }
        } catch (Exception e) {
            throw e;
        }
    }

    private Long ingresarIsbnABuscar() {
        System.out.println("Ingrese el isbn del libro que desea buscar: ");
        Long isbn = entrada.nextLong();
        return isbn;

    }

    public void obtenerLibroPorIsbn() throws Exception {
        try {
            Long isbn = ingresarIsbnABuscar();

            Libro libro = libroDAO.buscarPorId(isbn);

            if (libro == null) {
                throw new Exception("El libro no existe");
            } else {
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", "ISBN", "TITULO", "AÑO", "EJEMPLARES", "EJEMPLARES PRESTADOS", "EJEMPLARES RESTANTES", "ALTA", "AUTOR", "EDITORIAL");
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes(), libro.getAlta(), libro.getAutor().getNombre(), libro.getEditorial().getNombre());
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private String ingresarNombreABuscar() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        String nombreLibro = entrada.next();
        return nombreLibro;
    }

    public void obtenerLibroPorNombre() throws Exception {
        try {
            String nombre = ingresarNombreABuscar();

            List<Libro> libros = libroDAO.buscarPorNombre(nombre);

            if (libros == null) {
                throw new Exception("No existen libros");
            } else {
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", "ISBN", "TITULO", "AÑO", "EJEMPLARES", "EJEMPLARES PRESTADOS", "EJEMPLARES RESTANTES", "ALTA", "AUTOR", "EDITORIAL");

                for (Libro libro : libros) {
                    System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes(), libro.getAlta(), libro.getAutor().getNombre(), libro.getEditorial().getNombre());
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private Integer ingresarId() {
        Integer id = entrada.nextInt();
        return id;
    }

    public void obtenerLibroPorAutor() throws Exception {
        try {
            System.out.println("Lista de autores: ");
            autorServicio.imprimirTotal();
            System.out.println("Ingrese el ID del autor: ");
            Integer id = ingresarId();

            List<Libro> libros = libroDAO.buscarPorAutor(id);

            if (libros == null) {
                throw new Exception("No existen libros");
            } else {
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", "ISBN", "TITULO", "AÑO", "EJEMPLARES", "EJEMPLARES PRESTADOS", "EJEMPLARES RESTANTES", "ALTA", "AUTOR", "EDITORIAL");

                for (Libro libro : libros) {
                    System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes(), libro.getAlta(), libro.getAutor().getNombre(), libro.getEditorial().getNombre());
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void obtenerLibroPorEditorial() throws Exception {
        try {
            System.out.println("Lista de editoriales: ");
            editorialServicio.imprimirTotal();
            System.out.println("Ingrese el ID de la editorial: ");
            Integer id = ingresarId();

            List<Libro> libros = libroDAO.buscarPorEditorial(id);

            if (libros == null) {
                throw new Exception("No existen libros");
            } else {
                System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", "ISBN", "TITULO", "AÑO", "EJEMPLARES", "EJEMPLARES PRESTADOS", "EJEMPLARES RESTANTES", "ALTA", "AUTOR", "EDITORIAL");

                for (Libro libro : libros) {
                    System.out.printf("%-15s%-30s%-10s%-12s%-22s%-22s%-10s%-20s%-20s \n", libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes(), libro.getAlta(), libro.getAutor().getNombre(), libro.getEditorial().getNombre());
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarLibro() throws Exception {
        try {

            this.imprimirTotal();

            Long isbn = ingresarIsbnABuscar();

            Libro libro = libroDAO.buscarPorId(isbn);

            if (libro == null) {
                throw new Exception("No existe el libro");
            } else {
                libro.setAlta(false);
                libroDAO.eliminar(libro);
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
