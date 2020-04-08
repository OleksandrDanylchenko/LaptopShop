package ua.alexd.excelInteraction.exports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import ua.alexd.dateTimeService.DateTimeProvider;

@Service
@Lazy
public abstract class ExcelExporter extends AbstractXlsxView {
    @Autowired
    protected DateTimeProvider timeProvider;
    protected final RowsStylerBuilder rowsStylerBuilder;

    public ExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        this.rowsStylerBuilder = rowsStylerBuilder;
    }
}