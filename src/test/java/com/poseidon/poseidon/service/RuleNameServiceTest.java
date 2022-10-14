package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Optional<RuleName> actualRuleName = ruleNameService.getRuleNameById(1);

        assertEquals(expectedRuleName, actualRuleName.get());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteRuleName() {
        doNothing().when(ruleNameRepository).delete(expectedRuleName);

        ruleNameService.deleteRuleName(expectedRuleName);

        verify(ruleNameRepository, times(1)).delete(expectedRuleName);
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
