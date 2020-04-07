package ua.alexd.excelInteraction.exports;

import org.springframework.web.servlet.view.document.AbstractXlsxView;

public abstract class ExcelExporter extends AbstractXlsxView {
    protected final RowsStylerBuilder rowsStylerBuilder;

    public ExcelExporter(RowsStylerBuilder rowsStylerBuilder) {
        this.rowsStylerBuilder = rowsStylerBuilder;
    }
}