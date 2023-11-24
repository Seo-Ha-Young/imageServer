package image.server.controller;


import image.server.imageDTO.PageRequestDTO;
import image.server.imageDTO.TestDTO;
import image.server.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/wcs/image")
@RequiredArgsConstructor
@Log4j2
public class ImageController {
    private final ImageService imageService;


    @GetMapping("/view")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("view..........."+pageRequestDTO);
        model.addAttribute("result",imageService.getList(pageRequestDTO));
    }
    }


