/*
  Репозитория для работы с кешем Redis.
  Кеш не обязателен для работы, поэтому, если что-то пошло не так, пишем ошибку в лог и,
  если требуется что-то вернуть, то возвращаем пустое значение.
  Подключен планировщик, который чистит кеш для Подразделений каждые 30 минут
 */
package com.colvir.bootcamp.salary.repository;

import com.colvir.bootcamp.salary.model.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class DepartmentCacheRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(DepartmentCacheRepository.class);

    public Optional<Department> findById(Integer id) {
        try {
            String stringValue = redisTemplate.opsForValue().get("Department-" + id);
            if (stringValue == null) {
                return Optional.empty();
            } else {
                return Optional.of(objectMapper.readValue(stringValue, Department.class));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    public void save(Department department) {
        try {
            redisTemplate.opsForValue().set("Department-" + department.getId(), objectMapper.writeValueAsString(department));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelayString = "PT30M")
    public void clearAll() {
        try {
            Set<String> keys = redisTemplate.keys("Department-*");
            if ((keys != null ? keys.size() : 0) == 0) {
                return;
            }
            log.info(String.format("Очистка кэша, удаляется записей: %s", keys.size()));
            redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
