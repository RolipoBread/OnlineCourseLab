package com.example.onlinecourseslab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OnlinecourseslabApplicationMainTest {

    @Test
    void mainMethod_shouldRun() {
        assertDoesNotThrow(() ->
            OnlinecourseslabApplication.main(new String[]{})
        );
    }
}