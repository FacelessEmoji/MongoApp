package rut.miit.mongoapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rut.miit.mongoapp.models.City;

public interface CityRepository extends MongoRepository<City, String> {
    // Тут можно добавить кастомные методы, если они понадобятся
    City findByName(String name);
}

