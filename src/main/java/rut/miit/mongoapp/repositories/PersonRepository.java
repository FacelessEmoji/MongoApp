package rut.miit.mongoapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rut.miit.mongoapp.models.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
    // Тут можно добавить кастомные методы, если они понадобятся
}
