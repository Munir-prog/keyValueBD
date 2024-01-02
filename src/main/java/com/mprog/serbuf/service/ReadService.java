package com.mprog.serbuf.service;

import com.mprog.serbuf.model.ValueDto;
import com.mprog.serbuf.storage.StorageUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.mprog.serbuf.storage.StorageUtils.*;

@Service
public class ReadService {

    @Value("${serbuf.base.path}")
    private String basePath;

    @SneakyThrows
    public ValueDto getValFromStorage(String collection, String key) {
        File file = buildFile(basePath, collection);
        if (file.exists()) { // нашли коллекцию

            String newKey = replaceDividersColon(key);
            newKey = replaceDividersSemi(newKey);

            List<String> lines = getLinesFromFile(basePath, collection);
            int fileCount = StorageUtils.getFileCount(lines.get(0));

            // ищем значение по ключу из первого файла коллекции
            ValueDto valueDto = getValueFromFile(newKey, lines, 1);
            valueDto.setFileCount(fileCount);
            String value = valueDto.getValue();

            if (value == null) { // не нашли значение в первом файле коллекции
                for (int i = 1; i <= fileCount; i++) { // итерируемся по другим файлам коллекции в поисках нашего значения

                    List<String> nextLines = getLinesFromFile(basePath, collection + i);
                    ValueDto nextValueDto = getValueFromFile(newKey, nextLines, 0);

                    String nextValue = nextValueDto.getValue();
                    if (nextValue != null) {
                        nextValueDto.setFileCount(fileCount);
                        return normalizeValue(nextValueDto, String.valueOf(i));
                    }
                }
                throw new RuntimeException("Key: " + key + " not found!");
            } else {
                return normalizeValue(valueDto, "");
            }
        } else { // выкидываем исключение если нет такой коллекции
            throw new RuntimeException("Bad collection: " + collection);
        }
    }



    private String replaceDividersColon(String textToReplace) {
        return textToReplace.replaceAll(COLON, "*&^%");
    }

    private String replaceDividersSemi(String textToReplace) {
        return textToReplace.replaceAll(SEMICOLON, "%^&*");
    }

    private ValueDto getValueFromFile(String key, List<String> lines, int index) {
        String line = lines.get(index);
        int indexOfKey = line.indexOf(key);
        if (index == 1) {
            indexOfKey = indexOfKey + 4;
        }
        String value = Arrays.stream(line.split(SEMICOLON))
                .map(it -> it.split(COLON))
                .filter(it -> it[0].equals(key))
                .map(it -> it[1])
                .findFirst()
                .orElse(null);


        return ValueDto.builder()
                .value(value)
                .index(indexOfKey)
                .size((key + value).length() + 1)
                .build();
    }

    private ValueDto normalizeValue(ValueDto valueDto, String fileNum) {
        String value = valueDto.getValue();
        value = value.replaceAll("%\\^&\\*", SEMICOLON);
        value = value.replaceAll("\\*&\\^%", COLON);
        valueDto.setValue(value);
        valueDto.setFileNum(fileNum);
        return valueDto;
    }


}
