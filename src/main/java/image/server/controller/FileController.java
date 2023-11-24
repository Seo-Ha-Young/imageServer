package image.server.controller;


import image.server.imageDTO.TestDTO;
import image.server.payload.Response;
import image.server.service.ImageService;
import image.server.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//@RestController
@Controller
@RequestMapping("/wcs/image")
@RequiredArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final StorageService storageService;
    private final ImageService imageService;

    @GetMapping("/upload")
    public void upload() {}
    @PostMapping("/upload")
    public ResponseEntity<Response> uploadImage(@RequestParam("file") MultipartFile file,
                                                TestDTO testDTO) throws IOException {
        Response res = new Response();
        try{
            String result = storageService.saveFile(file);
            res.setImageLocation("/"+result);
            res.setMessage("done");
            res.setSuccess(true);
            testDTO.setImg_name(res.getImageLocation());
            System.out.println("testDTO : "+testDTO);
            Long no = imageService.register(testDTO);
            System.out.println("이미지 번호 : "+no);
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("failed");
            res.setSuccess(false);
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/upload")
    public ResponseEntity<Response> postImageUpload(@RequestParam("files") MultipartFile[] files,
                                                    @RequestParam("postName")String postName) {
        Response res = new Response();
        List<String> results = new ArrayList<>();
        List<String> imageLocations = new ArrayList<>();
        try{
            results = storageService.saveFiles(files, postName);
            for(String result : results){
                imageLocations.add("/"+postName+"/"+result);
            }
            res.setImageLocations(imageLocations);
            res.setMessage("done");
            res.setSuccess(true);
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("failed");
            res.setSuccess(false);
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/display/{userName}/{fileName:.+}")
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName,
                                                 @PathVariable String userName,
                                                 HttpServletRequest request) {
        // Load file as Resource
        Resource resource = storageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
