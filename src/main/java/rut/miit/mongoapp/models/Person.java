package rut.miit.mongoapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "people")
public class Person extends BaseEntity{
    @Field(name="firstName")
    private String firstName;

    @Field(name="lastName")
    private String lastName;

    @Field(name="age")
    private Integer age;

    @Field(name="gender")
    private String gender;

    @DBRef
    private City city;

    @DBRef
    private Pet pet;
}
//
//    // Связь с питомцем
//    @Field(name="petId")
//    private String petId;
//
//    // Связь с городом
//    @Field(name="cityId")
//    private String cityId;


