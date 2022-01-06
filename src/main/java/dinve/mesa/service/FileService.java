package dinve.mesa.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {
    public String save(MultipartFile file, String namepro)throws Exception;
    public Resource load(String name) throws  Exception;
    public void saves(List<MultipartFile> file, String namepro) throws  Exception;
    public Stream<Path>loadAll() throws Exception;

}
