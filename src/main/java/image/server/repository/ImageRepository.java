package image.server.repository;

import image.server.Entity.Test;
import image.server.imageDTO.TestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ImageRepository extends JpaRepository<Test, Long>,
        QuerydslPredicateExecutor<Test> {

}
