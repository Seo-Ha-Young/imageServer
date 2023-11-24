package image.board.Entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class})
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
}
