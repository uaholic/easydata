package com.guanyanqi.easydata.reader.source;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.guanyanqi.easydata.reader.config.FileInfoConfig;

/**
 * 文件输入源抽象
 *
 * <T>:文件单行读入内存的类型
 *
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public abstract class AbstractFileInputSource<T> implements InputSource {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFileInputSource.class);

    private final FileInfoConfig config;

    private final File sourceFile;

    public abstract Map<Integer, String> convertLine(T lineData);

    protected abstract T getLine(File file, String charSet);

    public AbstractFileInputSource(FileInfoConfig config) {
        logger.debug("Init input source by config:{}",config);
        this.config = config;
        this.sourceFile = new File(config.getFilePath());
    }

    @Override
    public List<Map<Integer, String>> getIndexMaps(Long partSize) {
        return getIndexMaps(sourceFile, config.getCharSet(), partSize, config.getStartLine(), config.getEndLine());
    }

    public abstract List<Map<Integer, String>> getIndexMaps(File file, String charSet, Long partSize, Long startLine,
                                                            Long endLine);

    public FileInfoConfig getConfig() {
        return config;
    }
}
