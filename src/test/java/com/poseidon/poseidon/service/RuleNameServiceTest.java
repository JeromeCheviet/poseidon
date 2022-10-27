package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;
import com.poseidon.poseidon.exception.RuleNameNotDeletedException;
import com.poseidon.poseidon.exception.RuleNameNotFoundException;
import com.poseidon.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService = new RuleNameServiceImpl();

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Mock
    private RuleName expectedRuleName;

    @BeforeEach
    private void setUp() {
        int expectedRuleNameId = 1;
        String expectedName = "name";
        String expectedDescription = "description";
        String expectedJson = "{ title: json }";
        String expectedTemplate = "template";
        String expectedSqlStr = "str";
        String expectedSqlPart = "part";

        expectedRuleName = new RuleName();
        expectedRuleName.setId(expectedRuleNameId);
        expectedRuleName.setName(expectedName);
        expectedRuleName.setDescription(expectedDescription);
        expectedRuleName.setJson(expectedJson);
        expectedRuleName.setTemplate(expectedTemplate);
        expectedRuleName.setSqlStr(expectedSqlStr);
        expectedRuleName.setSqlPart(expectedSqlPart);
    }

    @Test
    void testGetRuleNameList() {
        List<RuleName> expectedRuleNameList = new ArrayList<>();
        expectedRuleNameList.add(expectedRuleName);

        when(ruleNameRepository.findAll()).thenReturn(expectedRuleNameList);

        Iterable<RuleName> actualRuleNameList = ruleNameService.getRuleNames();

        assertEquals(expectedRuleNameList, actualRuleNameList);
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void testGetRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(expectedRuleName));

        RuleName actualRuleName = ruleNameService.getRuleNameById(1);

        assertEquals(expectedRuleName, actualRuleName);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void testGetRuleNameById_whenEmpty_returnException() {
        when(ruleNameRepository.findById(100)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuleNameNotFoundException.class, () -> {
            ruleNameService.getRuleNameById(100);
        });

        assertEquals("Rule name with id 100 not found", exception.getMessage());
        verify(ruleNameRepository, times(1)).findById(100);
    }

    @Test
    void testDeleteRuleName() {
        doNothing().when(ruleNameRepository).delete(expectedRuleName);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> ruleNameService.deleteRuleName(expectedRuleName)
        );

        verify(ruleNameRepository, times(1)).delete(expectedRuleName);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteRuleName_whenRuleNameIsPresent_returnException() {
        doNothing().when(ruleNameRepository).delete(expectedRuleName);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(expectedRuleName));

        Throwable exception = assertThrows(RuleNameNotDeletedException.class, () -> {
            ruleNameService.deleteRuleName(expectedRuleName);
        });

        assertEquals("Rule name with id 1 has not been deleted", exception.getMessage());
        verify(ruleNameRepository, times(1)).delete(expectedRuleName);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void testAddRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(expectedRuleName);

        ruleNameService.addRuleName(expectedRuleName);

        verify(ruleNameRepository, times(1)).save(expectedRuleName);
    }

    @Test
    void testUpdateRuleName() {
        expectedRuleName.setName("update name");
        int actualRuleNameId = 1;
        when(ruleNameRepository.save(expectedRuleName)).thenReturn(expectedRuleName);

        ruleNameService.updateRuleName(actualRuleNameId, expectedRuleName);

        assertEquals("update name", expectedRuleName.getName());
        assertEquals(actualRuleNameId, expectedRuleName.getId());
        verify(ruleNameRepository, times(1)).save(expectedRuleName);
    }

}
