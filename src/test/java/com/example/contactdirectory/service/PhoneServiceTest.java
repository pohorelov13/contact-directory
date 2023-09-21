package com.example.contactdirectory.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.contactdirectory.repo.PhoneRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PhoneService.class})
@ExtendWith(SpringExtension.class)
class PhoneServiceTest {
    @MockBean
    private PhoneRepo phoneRepo;

    @Autowired
    private PhoneService phoneService;

    /**
     * Method under test: {@link PhoneService#isPhoneExist(String)}
     */
    @Test
    void testWhenPhoneExist() {
        when(phoneRepo.existsByPhoneNumber(Mockito.<String>any())).thenReturn(true);
        assertTrue(phoneService.isPhoneExist("6625550144"));
        verify(phoneRepo).existsByPhoneNumber(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PhoneService#isPhoneExist(String)}
     */
    @Test
    void testWhenPhoneIsNotExist() {
        when(phoneRepo.existsByPhoneNumber(Mockito.<String>any())).thenReturn(false);
        assertFalse(phoneService.isPhoneExist("6625550144"));
        verify(phoneRepo).existsByPhoneNumber(Mockito.<String>any());
    }
}

