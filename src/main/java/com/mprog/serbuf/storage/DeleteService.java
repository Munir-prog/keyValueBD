package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.ValueDto;
import com.mprog.serbuf.service.ReadService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.mprog.serbuf.storage.StorageUtils.EXT;
import static com.mprog.serbuf.storage.StorageUtils.writeDataToFile;

@Service
@RequiredArgsConstructor
public class DeleteService {

    private final ReadService readService;

    @Value("${serbuf.base.path}")
    private String basePath;

    @SneakyThrows
    public void deleteValFromStorage(String collection, String key) {
        // поиск элемента по ключу
        ValueDto valueDto = readService.getValFromStorage(collection, key);

        // пустая строка для перезаписи
        String emptyVal = buildEmptyString(valueDto);

        // перезаписывем определенный участок файла
        writeDataToFile(basePath + collection + valueDto.getFileNum() + EXT, emptyVal, valueDto.getIndex());
    }

    private String buildEmptyString(ValueDto valueDto) {
        return " ".repeat(Math.max(0, valueDto.getSize()));
    }
}
