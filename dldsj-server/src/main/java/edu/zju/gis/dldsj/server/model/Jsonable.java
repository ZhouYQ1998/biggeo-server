package edu.zju.gis.dldsj.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/07/21
 * Comments 可用于发布ES索引的model的接口
 */
public interface Jsonable extends Serializable {
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    ObjectMapper mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT)).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static <T extends Jsonable> T fromJsonString(String json, Class<T> cls) throws IOException {
        return mapper.readValue(json, cls);
    }

    static <T extends Jsonable> T fromJsonBytes(byte[] json, Class<T> cls) throws IOException {
        return mapper.readValue(json, cls);
    }

    String id();

    default String toJsonString() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    default byte[] toJsonBytes() throws JsonProcessingException {
        return mapper.writeValueAsBytes(this);
    }

    default HashMap<String, Object> toHashMap() throws IOException {
        return mapper.readValue(toJsonBytes(), new TypeReference<HashMap<String, Object>>() {
        });
    }

    /**
     * 建立ES索引类型的字段映射信息
     *
     * @return JSON字符串
     */
    default String buildMappingSource() {
        Field[] fields = getClass().getDeclaredFields();
        JSONObject source = new JSONObject();
        source.put("dynamic", false).put("_all", new JSONObject()
                .put("analyzer", "ik_max_word")
                .put("search_analyzer", "ik_smart")
                .put("term_vector", "no").put("store", "yes"));
        JSONObject properties = new JSONObject();
        for (Field field : fields) {
            String typeName = field.getType().getName();
            JSONObject property = new JSONObject();
            switch (typeName) {
                case "short":
                case "java.lang.Short":
                    property.put("type", "short").put("store", true);
                    break;
                case "int":
                case "java.lang.Integer":
                    property.put("type", "integer").put("store", true);
                    break;
                case "long":
                case "java.lang.Long":
                    property.put("type", "long").put("store", true);
                    break;
                case "float":
                case "java.lang.Float":
                    property.put("type", "float").put("store", true);
                    break;
                case "double":
                case "java.lang.Double":
                case "java.math.BigDecimal":
                    property.put("type", "double").put("store", true);
                    break;
                case "byte":
                case "java.lang.Byte":
                    property.put("type", "byte").put("store", true);
                    break;
                case "char":
                case "java.lang.Character":
                    property.put("type", "text").put("store", true);
                    break;
                case "boolean":
                case "java.lang.Boolean":
                    property.put("type", "boolean").put("store", true);
                    break;
                case "java.lang.String":
                    property.put("type", "text").put("store", true).put("analyzer", "ik_max_word")
                            .put("search_analyzer", "ik_smart");
                    break;
                case "java.utils.Date":
                    property.put("type", "date").put("store", true).put("format", DEFAULT_DATE_FORMAT);
                    break;
            }
            properties.put(field.getName(), property);
        }
        source.put("properties", properties);
        return source.toString();
    }
}
