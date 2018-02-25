package pl.funnyqrz.mapper.dto;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Blob;
import java.time.LocalDate;

@Data
public class ReportDto {

    private BigInteger id;
    private String fileName;
    private LocalDate date;
    private Blob content;

}
