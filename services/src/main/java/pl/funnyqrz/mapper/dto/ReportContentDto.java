package pl.funnyqrz.mapper.dto;

import lombok.Data;

import java.sql.Blob;

@Data
public class ReportContentDto {

    private String name;
    private Blob content;
}
