package rut.miit.mongoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.mongoapp.models.City;
import rut.miit.mongoapp.repositories.CityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return new ResponseEntity<>(city.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable String id, @RequestBody City city) {
        if (cityRepository.existsById(id)) {
            city.setId(id);
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/averagePopulation")
    public double getAveragePopulation() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group().avg("population").as("averagePopulation")
        );

        AggregationResults<AverageResult> result = mongoTemplate.aggregate(agg, "cities", AverageResult.class);
        return result.getUniqueMappedResult().averagePopulation;
    }

    private static class AverageResult {
        public double averagePopulation;
    }//2185000.0

    @GetMapping("/averageAgeByCity")
    public List<CityAverageAge> getAverageAgeByCity() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("cities", "cityId", "_id", "cityData"),
                Aggregation.unwind("cityData"),
                Aggregation.group("cityData.name").avg("age").as("avgAge")
        );

        AggregationResults<CityAverageAge> results = mongoTemplate.aggregate(agg, "people", CityAverageAge.class);
        return results.getMappedResults();
    }

    // Вспомогательный класс для хранения результата
    public static class CityAverageAge {
        public String _id; // название города
        public double avgAge;
    }
}

