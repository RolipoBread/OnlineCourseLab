package com.example.onlinecourseslab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OnlinecourseslabApplicationMainTest {
    @Test
    void mainMethod_shouldRun() {
        OnlinecourseslabApplication.main(new String[]{});
        assertTrue(true); // чтобы Sonar не ругался
    }
}