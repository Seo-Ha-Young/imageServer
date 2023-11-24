package image.server.service;


import image.server.Entity.Test;
import image.server.imageDTO.PageRequestDTO;
import image.server.imageDTO.PageResultDTO;
import image.server.imageDTO.TestDTO;

import java.util.HashMap;
import java.util.Map;

public interface ImageService {
    Long register(TestDTO testDTO);

    default Map<String, Object> dtoToEntity(TestDTO testDTO) {
        Map<String, Object> entityMap = new HashMap<>();
        Test test = Test.builder()
                .img_name(testDTO.getImg_name())
                .build();
        entityMap.put("test", test);

        return entityMap;
    }

    default TestDTO entityToDto(Test entity) {
        return TestDTO.builder()
                .no(entity.getNo())
                .img_name(entity.getImg_name())
                .regDate(entity.getRegDate())
                .build();
    }
    PageResultDTO<TestDTO, Test> getList(PageRequestDTO requestDTO);

}
