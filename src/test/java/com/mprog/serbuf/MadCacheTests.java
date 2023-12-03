package com.mprog.serbuf;

import com.mprog.serbuf.service.StorageCreatorService;
import com.mprog.serbuf.service.impl.MainCommandService;
import com.mprog.serbuf.storage.MapMemoryStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
class MadCacheTests {
    private final static String A_KEY = "aKey";
    private final static String B_KEY = "bKey";
    private final static String C_KEY = "cKey";
    private final static String D_KEY = "dKey";
    private final static String ONE_KEY = "1Key";
    private final static String OTH_KEY = "$1Key";
    private final static String A_VALUE = "aValue";
    private final static String B_VALUE = "bValue";
    private final static String C_VALUE = "cValue";
    private final static String D_VALUE = "dValue";
    private final static String ONE_VALUE = "1Value";
    private final static String OTH_VALUE = "$@31Value";

    @Autowired
    private MainCommandService mainCommandService;
    @Autowired
    private StorageCreatorService storageCreatorService;


    @Test
    void shouldSetAndGet() {
        mainCommandService.set(A_KEY, A_VALUE);
        mainCommandService.set(B_KEY, B_VALUE);
        mainCommandService.set(C_KEY, C_VALUE);
        mainCommandService.set(D_KEY, D_VALUE);
        mainCommandService.set(ONE_KEY, ONE_VALUE);
        mainCommandService.set(OTH_KEY, OTH_VALUE);
        Assertions.assertThat(mainCommandService.get(A_KEY)).isEqualTo(A_VALUE);
        Assertions.assertThat(mainCommandService.get(B_KEY)).isEqualTo(B_VALUE);
        Assertions.assertThat(mainCommandService.get(C_KEY)).isEqualTo(C_VALUE);
        Assertions.assertThat(mainCommandService.get(D_KEY)).isEqualTo(D_VALUE);
        Assertions.assertThat(mainCommandService.get(ONE_KEY)).isEqualTo(ONE_VALUE);
        Assertions.assertThat(mainCommandService.get(OTH_KEY)).isEqualTo(OTH_VALUE);
    }

    @Test
    void shouldSetAndGetAndClear() {
        mainCommandService.set(A_KEY, A_VALUE);
        boolean actual = mainCommandService.clear(A_KEY);
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(mainCommandService.get(A_KEY)).isNull();
    }

    @Test
    void shouldGetAndDelete() {
        mainCommandService.set(A_KEY, A_VALUE);
        String actual = mainCommandService.getAndDelete(A_KEY);
        Assertions.assertThat(actual).isEqualTo(A_VALUE);
        Assertions.assertThat(mainCommandService.get(A_KEY)).isNull();
    }

    @Test
    void shouldAppend() {
        mainCommandService.set(A_KEY, A_VALUE);
        String actual = mainCommandService.append(A_KEY, B_VALUE);
        Assertions.assertThat(actual).isEqualTo(A_VALUE + B_VALUE);
    }

    @Test
    void shouldSetedExists() {
        mainCommandService.set(A_KEY, A_VALUE);
        boolean actual = mainCommandService.exists(A_KEY);
        Assertions.assertThat(actual).isTrue();
    }
}
