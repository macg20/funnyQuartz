package pl.funnyqrz.mapper;

import pl.funnyqrz.entities.ReportEntity;
import pl.funnyqrz.mapper.dto.NoContentReportDto;

public class NoContentReportMapper implements AbstractMapper<NoContentReportDto, ReportEntity> {

    private NoContentReportDto dto;

    @Override
    public NoContentReportDto mapToDTO(ReportEntity entity) {
        NoContentReportDto dto = new NoContentReportDto();
        dto.setId(entity.getId());
        dto.setName(entity.getFileName());
        dto.setDateTime(entity.getCreateDate());
        return dto;
    }

    @Override
    @Deprecated
    public ReportEntity mapToEntity(NoContentReportDto dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        return entity;
    }
}
