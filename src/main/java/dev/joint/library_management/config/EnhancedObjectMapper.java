package dev.joint.library_management.config;

import org.springframework.stereotype.Component;

import java.util.List;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
@Component
public class EnhancedObjectMapper {
        private final tools.jackson.databind.ObjectMapper objectMapper;

        public EnhancedObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public <D, E> E convertValue(D source, Class<E> targetType) {
            E result = objectMapper.convertValue(source, targetType);
            GenericRelationalBinder.bind(result);
            return result;
        }

        public <E, D> List<D> convertList(List<E> source, Class<D> targetType) {
            return objectMapper.convertValue(
                    source,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, targetType)
            );
        }
    }

