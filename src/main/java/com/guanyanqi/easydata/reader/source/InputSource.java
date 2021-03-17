package com.guanyanqi.easydata.reader.source;

import java.util.List;
import java.util.Map;

/**
 * 输入源
 *
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public interface InputSource {

    default List<Map<Integer, String>> getIndexMaps() {
        return getIndexMaps(null);
    }

    List<Map<Integer, String>> getIndexMaps(Long partSize);
}
