package rut.miit.mongoapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * todo Document type BaseEntity
 */
@Data
public class BaseEntity {
    @Id
    private String id;
}
