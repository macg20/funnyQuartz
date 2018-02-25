package pl.funnyqrz.mapper.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class NoContentReportDto {

    private BigInteger id;
    private String name;
    private LocalDate dateTime;
}
