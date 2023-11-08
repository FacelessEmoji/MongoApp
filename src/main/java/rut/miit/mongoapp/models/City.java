package rut.miit.mongoapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "cities")
public class City extends BaseEntity{
    @Field(name="name")
    private String name;

    @Field(name="population")
    private Integer population;

    @Field(name="landmark")
    private String landmark;

    @Field(name="age")
    private Integer age;
}

