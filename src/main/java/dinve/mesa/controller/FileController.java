package dinve.mesa.controller;

import dinve.mesa.converter.File;
import dinve.mesa.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, @RequestParam String namepro) throws Exception {
        if(file == null || file.isEmpty()){
            return  "Por favor seleccione un archivo";
        }
        return fileService.save(file, namepro);

    }

    @PostMapping("/uploads")
    public String uploadsFile(@RequestParam("files") List<MultipartFile> files, @RequestParam String namepro) throws Exception{
        if(files == null || files.isEmpty()){
            return  "Por favor seleccione un archivo";
        }
        fileService.saves(files, namepro);
        return "cargo exitoso";
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception{
        Resource resource = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<File>> getAllFiles() throws Exception{
        List<File> files = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",path.getFileName().toString()).build().toString();
            return new File(filename,url);

        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);

    }


}
