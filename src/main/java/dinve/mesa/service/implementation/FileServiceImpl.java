package dinve.mesa.service.implementation;

import dinve.mesa.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    private final Path rootFolder = Paths.get("uploads");

    @Override
    public String save(MultipartFile file, String namepro) throws Exception {
        StringBuilder nombre = new StringBuilder();
        nombre.append("~");
        nombre.append(namepro);
        nombre.append("~");
        nombre.append(file.getOriginalFilename());

        StringBuilder ruta = new StringBuilder();
        ruta.append(this.rootFolder);
        ruta.append(File.separator);
        ruta.append(nombre.toString());
        byte[] filesBytes = file.getBytes();
        Path path = Paths.get(ruta.toString());
        Files.write(path,filesBytes); // GUARDA Y REEMPLAZA EL ARCHIVO en ruta relativa

        return "\nNombre: "+nombre.toString()+
                "\nTipo: "+file.getContentType().toString()+
                "\nPath: "+String.valueOf(path);
    }

    @Override
    public Resource load(String name) throws Exception {
        Path file = rootFolder.resolve(name);
        Resource resource = new UrlResource(file.toUri());
        return resource;

        //return null;
    }

    @Override
    public void saves(List<MultipartFile> files, String namepro) throws Exception {
        for (MultipartFile file :files){
            this.save(file,namepro);
        }
        //return
    }

    @Override
    public Stream<Path> loadAll() throws Exception {

        return Files.walk(rootFolder,1).filter(path -> !path.equals(rootFolder)).map(rootFolder::relativize);
    }
}
