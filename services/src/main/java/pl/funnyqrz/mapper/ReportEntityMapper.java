package pl.funnyqrz.mapper;

import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mapper.dto.ReportDto;

public class ReportEntityMapper implements AbstractMapper<ReportDto,ReportEntity> {
    @Override
    public ReportDto mapToDTO(ReportEntity entity) {
        ReportDto dto = new ReportDto();
        dto.setId(entity.getId());
        dto.setFileName(entity.getFileName());
        dto.setDate(entity.getCreateDate());
        dto.setContent(entity.getFileContent());
        return dto;
    }

    @Override
    public ReportEntity mapToEntity(ReportDto dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        entity.setFileName(dto.getFileName());
        entity.setCreateDate(dto.getDate());
        entity.setFileContent(dto.getContent());
        return entity;
    }
}
