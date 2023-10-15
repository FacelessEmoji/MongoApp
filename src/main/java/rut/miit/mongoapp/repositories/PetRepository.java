package rut.miit.mongoapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rut.miit.mongoapp.models.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
    // Тут можно добавить кастомные методы, если они понадобятся
}
