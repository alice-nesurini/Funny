package tokenizer;

public enum TokenType{
    NIL,
    FALSE,
    TRUE,
    IF,
    IFNOT,
    THEN,
    ELSE,
    FI,
    WHILE,
    WHILENOT,
    DO,
    OD,
    PRINT,
    PRINTLN,
    ID,
    NUM,
    STRING,
    SEMICOLON,
    COMMA,
    ARROW,
    OPEN_ROUND_BRACKET,
    CLOSE_ROUND_BRACKET,
    OPEN_SQUARE_BRACKET,
    CLOSE_SQUARE_BRACKET,
    OPEN_CURLY_BRACKET,
    CLOSE_CURLY_BRACKET,
    NOT,
    STAR,
    DIVISION,
    MODULE,
    PLUS,
    MINUS,
    LESS,
    LESS_EQUALS,
    GREATER,
    GREATER_EQUALS,
    COMPARISON,
    DIFFERENCE,
    AND,
    OR,
    EQUALS,
    PLUS_EQUALS,
    MINUS_EQUALS,
    STAR_EQUALS,
    DIVISION_EQUALS,
    MODULE_EQUALS,
    EOS,
    UNKNOWN
}