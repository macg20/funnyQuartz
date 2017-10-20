package pl.funnyqrz.utils;

import java.time.LocalDate;

public class FileNameUtils {

    private static final String ATTACHMENT_PREFIX = "report_exchange_rate";
    private static final String ATTACHMENT_SUFFIX = ".pdf";

    public static final String createAttachmentReportName() {
        return String.format("%s_%s%s", ATTACHMENT_PREFIX, LocalDate.now().toString().replaceAll("-", "_"), ATTACHMENT_SUFFIX);

    }
}
