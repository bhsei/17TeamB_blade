package domain;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * Created this one by HMH on 2017/6/13.
 */
public class ExcelColumn<T> {
    private String header;
    private T[] values;
    private HSSFCellStyle style;

    public ExcelColumn(String header, T[] values) {
        this.header = header;
        this.values = values;
    }

    public ExcelColumn(String header, T[] values, HSSFCellStyle style) {
        this.header = header;
        this.values = values;
        this.style = style;
    }

    public HSSFCellStyle getStyle() {
        return style;
    }

    public void setStyle(HSSFCellStyle style) {
        this.style = style;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T[] getValues() {
        return values;
    }

    public void setValues(T[] values) {
        this.values = values;
    }
}
