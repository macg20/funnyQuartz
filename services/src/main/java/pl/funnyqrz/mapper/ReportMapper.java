package pl.funnyqrz.mapper;

import org.springframework.stereotype.Component;
import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mapper.dto.ReportDto;

@Component
public class ReportMapper implements AbstractMapper<ReportDto, ReportEntity> {
    @Override
    public ReportDto toDto(ReportEntity entity) {
        ReportDto dto = new ReportDto();
        dto.setId(entity.getId());
        dto.setFileName(entity.getFileName());
        dto.setDate(entity.getCreateDate());
        return dto;
    }

    @Override
    public ReportEntity toEntity(ReportDto dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        entity.setFileName(dto.getFileName());
        entity.setCreateDate(dto.getDate());
        return entity;
    }
}
