package pl.funnyqrz.mappers.dto;

import lombok.Data;

import java.sql.Blob;

@Data
public class ReportContentDto {

    private String name;
    private byte[] content;
}
