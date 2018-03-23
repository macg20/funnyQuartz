package pl.funnyqrz.mappers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class ReportDto {

    private BigInteger id;
    private String fileName;
    @JsonSerialize(using =LocalDateSerializer.class)
    private LocalDate date;

}
