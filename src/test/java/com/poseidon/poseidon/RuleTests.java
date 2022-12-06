package com.poseidon.poseidon;

import com.poseidon.poseidon.domain.RuleName;
import com.poseidon.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/schema.sql")
class RuleTests {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    void ruleTest() {
        RuleName rule = new RuleName();
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL");
        rule.setSqlPart("SQL Part");

        // Save
        rule = ruleNameRepository.save(rule);
        assertNotNull(rule.getId());
        assertTrue(rule.getName().equals("Rule Name"));

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        assertTrue(rule.getName().equals("Rule Name Update"));

        // Find
        Iterable<RuleName> ruleNames = ruleNameRepository.findAll();
        List<RuleName> listResult = new ArrayList<>();
        ruleNames.forEach(listResult::add);
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        assertFalse(ruleList.isPresent());
    }
}
