package com.guanyanqi.easydata.writer.executor;


import com.guanyanqi.easydata.common.utils.QiUtils;
import com.guanyanqi.easydata.writer.resolver.GenerateMapperResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class ExcelEasyWriter {

    private static final Logger logger = LoggerFactory.getLogger(CsvEasyWriter.class);

    private static final String defaultEncoding = "UTF-8";

    public <T> long writeObjects(List<T> objects, Class<T> objectType, SXSSFWorkbook workbook, String sheetName,
                                        boolean append) {
        GenerateMapperResolver<T> resolver = new GenerateMapperResolver<>();
        List<Field> fields = resolver.generateColumnFields(objectType);
        Sheet sheet = workbook.getSheet(sheetName);
        if (Objects.isNull(sheet)) {
            sheet = workbook.createSheet(sheetName);
        }
        int startRowNum = 0;
        if (append) {
            int lastRowNum = sheet.getLastRowNum();
            startRowNum = lastRowNum == 0 && sheet.getPhysicalNumberOfRows() == 0 ? 0 : lastRowNum + 1;
        }
        for (T object : objects) {
            Row row = sheet.createRow(startRowNum);
            Map<Field, String> fieldValueMap = resolver.resolveMapper(object, fields);
            for (int i = 0; i < fields.size(); i++) {
                String value = QiUtils.replaceNull(fieldValueMap.get(fields.get(i)), StringUtils.EMPTY);
                row.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(value);
            }
            startRowNum++;
        }
        return objects.size();
    }

    public <T> List<String> writeTitle(Class<T> objectType, SXSSFWorkbook workbook, String sheetName) {
        List<String> result = new ArrayList<>();
        GenerateMapperResolver<T> resolver = new GenerateMapperResolver<>();
        List<Field> fields = resolver.generateColumnFields(objectType);
        Map<Field, String> titleMap = resolver.generateTitleMap(fields);
        Sheet sheet = workbook.getSheet(sheetName);
        if (Objects.isNull(sheet)) {
            sheet = workbook.createSheet(sheetName);
        }
        Row row = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            String value = QiUtils.replaceNull(titleMap.get(fields.get(i)), StringUtils.EMPTY);
            row.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(value);
            result.add(value);
        }
        return result;
    }
}
