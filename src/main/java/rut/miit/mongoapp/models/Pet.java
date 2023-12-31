package rut.miit.mongoapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "pets")
public class Pet extends BaseEntity{
    @Field(name="name")
    private String name;

    @Field(name="type")
    private String type;

    @Field(name="age")
    private Integer age;

    @DBRef
    private List<Person> people;

}
