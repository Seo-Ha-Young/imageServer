package image.board.repository;

import image.board.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ImageRepository extends JpaRepository<Test, Long>,
        QuerydslPredicateExecutor<Test> {

}
