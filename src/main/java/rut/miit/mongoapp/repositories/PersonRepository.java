package rut.miit.mongoapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rut.miit.mongoapp.models.Person;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    // Тут можно добавить кастомные методы, если они понадобятся
    List<Person> findByCityId(String cityId);

}
