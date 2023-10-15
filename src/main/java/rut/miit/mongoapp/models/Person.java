package rut.miit.mongoapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "people")
public class Person {
    @Id
    private String id;

    @Field(name="firstName")
    private String firstName;

    @Field(name="lastName")
    private String lastName;

    @Field(name="age")
    private Integer age;

    @Field(name="gender")
    private String gender;

    // Связь с питомцем
    @Field(name="petId")
    private String petId;

    // Связь с городом
    @Field(name="cityId")
    private String cityId;
}

