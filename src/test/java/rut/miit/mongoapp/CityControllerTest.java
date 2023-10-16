package rut.miit.mongoapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rut.miit.mongoapp.controllers.CityController;
import rut.miit.mongoapp.models.City;
import rut.miit.mongoapp.repositories.CityRepository;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CityControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Test
    @Order(1)
    public void testConnection() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void testGetAllCities() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
        // ... дополнительные проверки для содержимого ответа
    }

    @Test
    @Order(3)
    public void testGetCityById() throws Exception {
        mockMvc.perform(get("/api/cities/6505c46fc35b3109b9dc8794"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Los Angeles"))
                .andExpect(jsonPath("$.population").value(4000000))
                .andExpect(jsonPath("$.landmark").value("Hollywood Sign"))
                .andExpect(jsonPath("$.age").value(240));
    }

    @Test
    @Order(4)
    public void testUpdateCity() throws Exception {
        String newCityJson = """
    {
        "id": "6505c46fc35b3109b9dc8794",
        "name": "Los Angeles",
        "population": 4200000,
        "landmark": "Hollywood Sign",
        "age": 245
    }
    """;

        String oldCityJson = """
{
    "id": "6505c46fc35b3109b9dc8794",
    "name": "Los Angeles",
    "population": 4000000,
    "landmark": "Hollywood Sign",
    "age": 240
}
    """;

        mockMvc.perform(put("/api/cities/6505c46fc35b3109b9dc8794")
                        .contentType("application/json")
                        .content(newCityJson))
                .andExpect(status().isOk());

        // После обновления проверьте, что данные в базе изменились
        City updatedCity = cityRepository.findById("6505c46fc35b3109b9dc8794").orElse(null);
        Assertions.assertNotNull(updatedCity);  // проверяем, что город действительно найден
        Assertions.assertEquals("Los Angeles", updatedCity.getName());
        Assertions.assertEquals(Integer.valueOf(4200000), updatedCity.getPopulation());
        Assertions.assertEquals("Hollywood Sign", updatedCity.getLandmark());
        Assertions.assertEquals(Integer.valueOf(245), updatedCity.getAge());

        mockMvc.perform(put("/api/cities/6505c46fc35b3109b9dc8794")
                        .contentType("application/json")
                        .content(oldCityJson))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5) // или другой порядковый номер, в зависимости от ваших потребностей
    public void testCreateCity() throws Exception {
        String cityJson = """
    {
        "name": "Phoenix",
        "population": 1680992,
        "landmark": "Camelback Mountain",
        "age": 155
    }
    """;

        mockMvc.perform(post("/api/cities")
                        .contentType("application/json")
                        .content(cityJson))
                .andExpect(status().isOk());

        // Проверка, что город был действительно создан
        City createdCity = cityRepository.findByName("Phoenix");
        Assertions.assertNotNull(createdCity);
        Assertions.assertEquals(1680992, createdCity.getPopulation());
        Assertions.assertEquals("Camelback Mountain", createdCity.getLandmark());
        Assertions.assertEquals(155, createdCity.getAge());
    }

    @Test
    @Order(6) // или другой порядковый номер, в зависимости от ваших потребностей
    public void testDeleteCity() throws Exception {
        City phoenix = cityRepository.findByName("Phoenix");
        Assertions.assertNotNull(phoenix);  // Убедитесь, что город Phoenix существует перед удалением

        String cityId = phoenix.getId();

        mockMvc.perform(delete("/api/cities/" + cityId))
                .andExpect(status().isNoContent());

        // Проверка, что город действительно был удален
        City deletedCity = cityRepository.findById(cityId).orElse(null);
        Assertions.assertNull(deletedCity);
    }

    @Test
    @Order(7)
    public void testGetAveragePopulation() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cities/averagePopulation"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        double averagePopulation = Double.parseDouble(responseBody);

        Assertions.assertEquals(2185000.0, averagePopulation, 0.1); // добавляем небольшой дельта
    }
}

