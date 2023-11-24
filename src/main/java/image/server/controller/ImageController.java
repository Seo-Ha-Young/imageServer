package image.server.controller;


import image.server.config.FileStorageProperties;
import image.server.imageDTO.PageRequestDTO;
import image.server.imageDTO.TestDTO;
import image.server.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/wcs/image")
@RequiredArgsConstructor
@Log4j2
public class ImageController {
    private final ImageService imageService;
    private  final FileStorageProperties uploadDir;

    @GetMapping("/view")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("view..........."+pageRequestDTO);
        model.addAttribute("result",imageService.getList(pageRequestDTO));
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFiles(String fileName, String size) {
        //  ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : "+srcFileName);
            File file = new File(uploadDir.getUploadDir()+File.separator+srcFileName);
            if(size != null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }
            log.info("file : "+file);
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //  result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //return result;
    }

}


