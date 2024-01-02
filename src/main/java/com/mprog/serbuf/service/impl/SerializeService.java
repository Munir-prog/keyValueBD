package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.service.FileService;
import com.mprog.serbuf.utils.MethodUtils;
import com.mprog.serbuf.utils.TypeSelector;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SerializeService {

//    private final ObjectWriter objectWriter;
//    private final DeserializeService deserializeService;


    public void serialize(List<Object> objects, String fileName) {
        if (objects == null || objects.isEmpty())
            throw new RuntimeException("empty dataset");
        StringBuilder data = new StringBuilder();
        Class<?> clazz = objects.get(0).getClass();
        data.append(clazz.getName());
        data.append("=");
        for (Object object : objects) {
            serialize(object, data);
            data.append("=");
        }

        String result = data.toString();
        FileService.writeToFile(fileName, result.getBytes());
    }


    @SneakyThrows
    private void serialize(Object object, StringBuilder data) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            data.append(field.getName());
            data.append("&");
            Method method = MethodUtils.getMethod("get", field.getName(), clazz, null);
            Object fieldVal = method.invoke(object);
            if (field.getType().isAssignableFrom(String.class)) {
                String value = (String) fieldVal;
                value = value.replaceAll("&", "#\\$)");
                value = value.replaceAll("\\^", ")\\$#");
                value = value.replaceAll("=", "(*?");
                data.append(value);
            } else {
                data.append(fieldVal);
            }
            data.append("&");
            data.append(TypeSelector.getType(field));
            data.append("^");
        }
    }












//    @SneakyThrows
//    public Integer serialize(Object object) {
//        String json = objectWriter.writeValueAsString(object);
//        String result = object.getClass().getName() + json;
//        byte[] bytes = result.getBytes();
//        deserializeService.deserialize(bytes);
//
//        return bytes.length;
//    }

}
