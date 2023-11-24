package image.server.service;

import image.server.Entity.Test;
import image.server.imageDTO.TestDTO;
import image.server.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Long register(TestDTO testDTO) {
        Map<String, Object> entityMap = dtoToEntity(testDTO);
        Test test = (Test) entityMap.get("test");
        imageRepository.save(test);
        return test.getNo();
    }


}
