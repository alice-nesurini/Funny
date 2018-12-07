package parser;

import org.junit.jupiter.api.Test;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScopeTest {

    private Scope scope;

    @Test
    void add() {
        List<String> ids=new ArrayList<>();
        Token tokenVar=new Token(TokenType.ID, "var");
        Token tokenName=new Token(TokenType.ID, "name");
        ids.add(tokenVar.getStringValue());
        ids.add(tokenName.getStringValue());
        scope =new Scope(ids, null);

        assertTrue(scope.contains("var"));
        assertTrue(scope.contains("name"));
        assertFalse(scope.contains("notAVariable"));
        assertEquals(1, scope.recursionLevel());
        scope.viewLookupTable();
    }

    @Test
    void addSuperior() {
        List<String> ids=new ArrayList<>();
        Token tokenVar=new Token(TokenType.ID, "var");
        Token tokenName=new Token(TokenType.ID, "name");
        ids.add(tokenVar.getStringValue());
        ids.add(tokenName.getStringValue());
        scope =new Scope(ids, null);

        List<String> internalIds=new ArrayList<>();
        internalIds.add(new Token(TokenType.ID, "x").getStringValue());
        Scope internalScope = new Scope(internalIds, scope);

        internalScope.viewLookupTable();

        assertTrue(internalScope.contains("var"));
        assertTrue(internalScope.contains("name"));
        assertTrue(internalScope.contains("x"));
        assertFalse(internalScope.contains("notAVariable"));
        assertEquals(2, internalScope.recursionLevel());
    }
}