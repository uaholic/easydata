package com.guanyanqi.easydata.reader.source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;



import com.guanyanqi.easydata.reader.config.FileInfoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instances of {@code AbstractTextFileInputSource} are not safe for use by multiple threads.
 *
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public abstract class AbstractTextFileInputSource extends AbstractFileInputSource<String> implements SourceType {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTextFileInputSource.class);

    private LineIterator it;

    // source已读总行数
    private long currentReadLine = 1L;

    private final String separator;

    public AbstractTextFileInputSource(FileInfoConfig config) {
        super(config);
        this.separator = separator();
    }

    protected abstract String separator();

    @Override
    protected String getLine(File file, String charSet) {
        if (Objects.isNull(it)) {
            initFileLineIterator(file, charSet);
        }
        return it.hasNext() ? it.nextLine() : null;
    }

    private void initFileLineIterator(File file, String charSet) {
        try {
            it = FileUtils.lineIterator(file, charSet);
        } catch (IOException e) {
            throw new RuntimeException("read file exception");
        }
    }

    private void close() {
        if (Objects.nonNull(it)) {
            LineIterator.closeQuietly(it);
        }
    }

    @Override
    public List<Map<Integer, String>> getIndexMaps(File file, String charSet, Long partSize, Long startLine,
                                                   Long endLine) {
        List<Map<Integer, String>> result = new ArrayList<>(1000000);

        if (Objects.isNull(startLine)) {
            startLine = 1L;
        }

        if (partSize == null || partSize < 1) {
            // 全量读取文件
            logger.debug("Reading all lines from file:{}", file.getPath());
            List<String> allLines = readAllLines(file, charSet);
            currentReadLine = allLines.size();
            for (String line : allLines) {
                result.add(convertLine(line));
            }
            return result;
        }

        logger.debug("Reading {} - {} lines from file:{}",
                currentReadLine,
                Math.min(currentReadLine + partSize, Objects.nonNull(endLine) ? endLine : currentReadLine + partSize),
                file.getPath());

        // 本次已读取的行数
        long lineCount = 1L;
        // 单次读取行数未超过分批大小限制 读取总行数未超过指定的读取范围
        while (lineCount <= partSize && (endLine == null || currentReadLine <= endLine)) {
            String line = getLine(file, charSet);
            // 已经读取到文件尽头
            if (line == null) {
                close();
                logger.debug("End of reading , total read line:{}", currentReadLine - 1);
                break;
            }
            if (currentReadLine < startLine) {
                currentReadLine++;
                continue;
            }
            result.add(convertLine(line));
            lineCount++;
            currentReadLine++;
        }
        if (endLine != null && currentReadLine >= endLine) {
            logger.debug("End of reading , total read line:{}", currentReadLine - 1);
            close();
        }
        return result;
    }

    @Override
    public Map<Integer, String> convertLine(String lineData) {
        String[] params = lineData.split(separator);
        Map<Integer, String> indexMap = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            indexMap.put(i + 1, params[i]);
        }
        return indexMap;
    }

    private List<String> readAllLines(File file, String charSet) {
        try {
            return FileUtils.readLines(file, charSet);
        } catch (IOException e) {
            throw new RuntimeException("read file exception");
        }
    }

    @Override
    public String getType() {
        return SourceTypeEnum.TEXT.getType();
    }

    public long getCurrentReadLine() {
        return currentReadLine;
    }
}
