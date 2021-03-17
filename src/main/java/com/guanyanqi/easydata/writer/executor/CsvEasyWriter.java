package com.guanyanqi.easydata.writer.executor;

import com.google.common.collect.Lists;
import com.guanyanqi.easydata.writer.resolver.GenerateCsvLineResolver;
import com.guanyanqi.easydata.writer.resolver.GenerateStringResolver;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 将Object写成文件的服务
 *
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */

@Service
public class CsvEasyWriter {

    private static final Logger logger = LoggerFactory.getLogger(CsvEasyWriter.class);

    private static final String defaultEncoding = "UTF-8";

    private static final String defaultLineEnding = "\n";

    private static final byte[] csvHeader = new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};


    public <T> long writeObjects(List<T> objects, Class<T> objectType, File file, boolean append) {
        GenerateStringResolver<T> resolver = new GenerateCsvLineResolver<>();
        List<Field> fields = resolver.generateColumnFields(objectType);
        List<String> contents = resolver.resolve(objects, fields);
        writeLines(file, contents, append);
        return contents.size();
    }

    public <T> String writeTitle(Class<T> objectType, File file) {
        GenerateStringResolver<T> resolver = new GenerateCsvLineResolver<>();
        List<Field> fields = resolver.generateColumnFields(objectType);
        String title = resolver.generateTitle(fields);
        writeCsvHeader(file);
        writeLine(file, title, true);
        return title;
    }

    public void writeLine(File file, String line, boolean append) {
        writeLines(file, Lists.newArrayList(line), append);
    }

    public void writeLines(File file, List<String> lines, boolean append) {
        writeLines(file, defaultEncoding, lines, defaultLineEnding, append);
    }

    public void writeLines(File file, String encoding, List<String> lines, String lineEnding, boolean append) {
        try {
            FileUtils.writeLines(file, encoding, lines, lineEnding, append);
        } catch (IOException e) {
            throw new RuntimeException("写入文件异常，文件名：" + file.getName());
        }
    }

    private void writeCsvHeader(File file){
        try {
            FileUtils.writeByteArrayToFile(file,csvHeader,false);
        } catch (IOException e) {
            logger.error("写入CSV文件Header异常",e);
            throw new RuntimeException("写入CSV文件Header异常，文件名：" + file.getName());
        }
    }
}
