package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.*;
import com.example.onlinecourseslab.repository.ProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressServiceImplTest {

    @Mock
    private ProgressRepository repository;

    @InjectMocks
    private ProgressServiceImpl service;

    @Test
    void markCompleted_shouldSaveProgress() {
        User user = new User();
        Lesson lesson = new Lesson();

        when(repository.findByStudentAndLesson(user, lesson))
            .thenReturn(Optional.empty());

        when(repository.save(any())).thenReturn(new Progress());

        Progress result = service.markCompleted(user, lesson);

        assertNotNull(result);
        verify(repository).save(any());
    }
}