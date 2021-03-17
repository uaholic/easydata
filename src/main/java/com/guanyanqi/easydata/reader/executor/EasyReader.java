package com.guanyanqi.easydata.reader.executor;

import java.util.List;




import com.guanyanqi.easydata.reader.resolver.FieldIndexMappingResolver;
import com.guanyanqi.easydata.reader.source.InputSource;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class EasyReader {

    private FieldIndexMappingResolver resolver;

    public <T> List<T> readObjects(InputSource inputSource, Class<T> objectClass, Long partSize) {
        return resolver.resolve(inputSource.getIndexMaps(partSize), objectClass);
    }

    public void setResolver(FieldIndexMappingResolver resolver) {
        this.resolver = resolver;
    }

}
