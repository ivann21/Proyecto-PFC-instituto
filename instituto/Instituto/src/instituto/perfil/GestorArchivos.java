/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.perfil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GestorArchivos {

    private static final String CARPETA_ARCHIVOS = "src/archivos/";

    public void subirArchivo(int idUsuario, String nombreArchivoReal, byte[] contenidoArchivo) {
    String rutaCarpetaUsuario = CARPETA_ARCHIVOS + idUsuario + "/";
    File carpetaUsuario = new File(rutaCarpetaUsuario);
    
    if (!carpetaUsuario.exists()) {
        carpetaUsuario.mkdirs();
    }

    try {
        Path rutaArchivo = Paths.get(rutaCarpetaUsuario + nombreArchivoReal);
        Files.write(rutaArchivo, contenidoArchivo);
    } catch (IOException e) { 
        e.printStackTrace();
    }
}

        public byte[] descargarArchivo(int idUsuario, String nombreArchivoReal) {
        String rutaCarpetaUsuario = CARPETA_ARCHIVOS + idUsuario + "/";
        Path rutaArchivo = Paths.get(rutaCarpetaUsuario + nombreArchivoReal);

        try {
            if (Files.exists(rutaArchivo)) {
                return Files.readAllBytes(rutaArchivo);
            } else {
                System.out.println("El archivo no existe: " + rutaArchivo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

  public List<?> obtenerArchivosUsuario(int idUsuario, boolean devolverObjetosArchivo) {
    String rutaCarpetaUsuario = CARPETA_ARCHIVOS + idUsuario + "/";
    File carpetaUsuario = new File(rutaCarpetaUsuario);

    if (devolverObjetosArchivo) {
        List<Archivo> archivosUsuario = new ArrayList<>();
        if (carpetaUsuario.exists() && carpetaUsuario.isDirectory()) {
            File[] archivos = carpetaUsuario.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        String nombreArchivo = archivo.getName();
                        String[] partesNombre = nombreArchivo.split("\\.");
                        if (partesNombre.length > 1) {
                            String nombrePersonalizado = partesNombre[0];
                            String extension = partesNombre[1];
                            archivosUsuario.add(new Archivo(nombrePersonalizado, nombreArchivo, extension));
                        }
                    }
                }
            }
        }
        return archivosUsuario;
    } else {
        List<String> archivosUsuario = new ArrayList<>();
        if (carpetaUsuario.exists() && carpetaUsuario.isDirectory()) {
            File[] archivos = carpetaUsuario.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    archivosUsuario.add(archivo.getName());
                }
            }
        }
        return archivosUsuario;
    }
}
   public boolean eliminarArchivo(int idUsuario, String nombreArchivoReal) {
        String rutaArchivo = CARPETA_ARCHIVOS + idUsuario + "/" + nombreArchivoReal;
        File archivo = new File(rutaArchivo);

        if (archivo.exists()) {
            return archivo.delete();
        } else {
            return false;
        }
    }
}