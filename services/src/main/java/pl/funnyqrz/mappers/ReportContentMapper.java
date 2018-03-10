package pl.funnyqrz.mappers;

import org.springframework.stereotype.Component;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mappers.dto.ReportContentDto;

@Component
public class ReportContentMapper implements AbstractMapper<ReportContentDto, ReportEntity> {

    private ReportContentDto dto;

    @Override
    public ReportContentDto toDto(ReportEntity entity) {
        ReportContentDto dto = new ReportContentDto();
        dto.setName(entity.getFileName());
        dto.setContent(entity.getFileContent());
        return dto;
    }

    @Override
    public ReportEntity toEntity(ReportContentDto dto) {
        ReportEntity entity = new ReportEntity();
        entity.setFileName(dto.getName());
        entity.setFileContent(dto.getContent());
        return entity;
    }
}
