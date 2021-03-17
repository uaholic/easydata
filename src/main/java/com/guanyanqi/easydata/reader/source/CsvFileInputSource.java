package com.guanyanqi.easydata.reader.source;


import com.guanyanqi.easydata.reader.config.FileInfoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class CsvFileInputSource extends AbstractTextFileInputSource implements SourceType {

    private static final Logger logger = LoggerFactory.getLogger(CsvFileInputSource.class);

    public CsvFileInputSource(FileInfoConfig config) {
        super(config);
    }

    @Override
    protected String separator() {
        String separator = ",";
        logger.debug("Declare the csv file separator as '{}'.", separator);
        return separator;
    }
}