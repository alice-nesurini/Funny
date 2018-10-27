package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LookupTableTest {

    private LookupTable lookupTable;

    @Test
    void add() {
        List<String> ids=new ArrayList<>();
        Token tokenVar=new Token(TokenType.ID, "var");
        Token tokenName=new Token(TokenType.ID, "name");
        ids.add(tokenVar.getStringValue());
        ids.add(tokenName.getStringValue());
        lookupTable=new LookupTable(ids, null);

        assertTrue(lookupTable.contains("var"));
        assertTrue(lookupTable.contains("name"));
        assertFalse(lookupTable.contains("notAVariable"));
        assertEquals(1, lookupTable.recursionLevel());
        lookupTable.viewLookupTable();
    }

    @Test
    void addSuperior() {
        List<String> ids=new ArrayList<>();
        Token tokenVar=new Token(TokenType.ID, "var");
        Token tokenName=new Token(TokenType.ID, "name");
        ids.add(tokenVar.getStringValue());
        ids.add(tokenName.getStringValue());
        lookupTable=new LookupTable(ids, null);

        List<String> internalIds=new ArrayList<>();
        internalIds.add(new Token(TokenType.ID, "x").getStringValue());
        LookupTable internalLookupTable = new LookupTable(internalIds, lookupTable);

        internalLookupTable.viewLookupTable();

        assertTrue(internalLookupTable.contains("var"));
        assertTrue(internalLookupTable.contains("name"));
        assertTrue(internalLookupTable.contains("x"));
        assertFalse(internalLookupTable.contains("notAVariable"));
        assertEquals(2, internalLookupTable.recursionLevel());
    }
}