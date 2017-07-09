package pl.funnyqrz.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return Optional.ofNullable(attribute).map(Date::valueOf).orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : dbData.toLocalDate();
    }
}
