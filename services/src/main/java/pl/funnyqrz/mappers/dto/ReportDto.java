package pl.funnyqrz.mappers.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class ReportDto {

    private BigInteger id;
    private String fileName;
    private LocalDate date;

}
