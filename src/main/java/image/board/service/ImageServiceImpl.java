package image.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import image.board.Entity.QTest;
import image.board.Entity.Test;
import image.board.imageDTO.PageRequestDTO;
import image.board.imageDTO.PageResultDTO;
import image.board.imageDTO.TestDTO;
import image.board.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Long register(TestDTO testDTO) {
        Map<String, Object> entityMap = dtoToEntity(testDTO);
        Test test = (Test) entityMap.get("test");
        imageRepository.save(test);
        return test.getNo();
    }

    @Override
    public PageResultDTO<TestDTO, Test> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("no").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Test> result = imageRepository.findAll(booleanBuilder, pageable);
        Function<Test, TestDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QTest qTest = QTest.test;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qTest.no.gt(0L);
        booleanBuilder.and(expression);
        if(type == null || type.trim().length() == 0) {
            return  booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("c")){
            conditionBuilder.or(qTest.img_name.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}
