package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.ValueDto;
import com.mprog.serbuf.service.ReadService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.mprog.serbuf.storage.StorageUtils.*;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final ReadService readService;
    private final WriteService writeService;
    private final DeleteService deleteService;

    @Value("${serbuf.base.path}")
    private String basePath;

    @SneakyThrows
    public void updateValInStorage(String collection, String key, String value) {
        // поиск элемента по ключу
        ValueDto valueDto = readService.getValFromStorage(collection, key);

        key = replaceDividers(key);
        value = replaceDividers(value);

        if ((key + value).length() < valueDto.getSize()) { // перезаписываем существующую пару key-value
            String toWrite = appendEmptyString(key + COLON + value, valueDto.getSize());
            writeDataToFile(basePath + collection + valueDto.getFileNum() + EXT, toWrite, valueDto.getIndex());
        } else { // записываем в конец коллекции
            deleteService.deleteValFromStorage(collection, key);
            writeService.setValToStorage(collection, key, value);
        }
    }


    private String appendEmptyString(String val, Integer size) {
        int valSize = val.length();
        val = val + " ".repeat(Math.max(0, size - valSize));
        return val;
    }
}
