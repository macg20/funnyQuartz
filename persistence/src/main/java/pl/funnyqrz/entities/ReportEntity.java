package pl.funnyqrz.entities;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@GenericGenerator(
        name = "report_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "report_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")
        }
)
@Table(name = "reports")
@Data
public class ReportEntity {

    @Id
    @GeneratedValue(generator = "report_generator")
    private BigInteger id;
    private String fileName;
    private Blob fileContent;
    private LocalDateTime createDate;

}
