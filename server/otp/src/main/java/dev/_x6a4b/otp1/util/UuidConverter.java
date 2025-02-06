package dev._x6a4b.otp1.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;
import static java.util.Optional.ofNullable;

@Converter
public class UuidConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return ofNullable(uuid).map(u -> u.toString()).orElse(null);
    }

    @Override
    public UUID convertToEntityAttribute(String s) {
        return ofNullable(s).map(u -> UUID.fromString(u)).orElse(null);
    }
}
