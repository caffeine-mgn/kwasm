package pw.binom.wit.parser

enum class TokenType {
    LINE_COMMENT,
    MULTI_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_END,
    WORD,
    COLON,
    OPEN_BRACE,
    CLOSE_BRACE,
    OPEN_PAREN,
    CLOSE_PAREN,
    COMMA,
    SPACE,
    TERMINATOR,
    AT,
    DIGITAL,
    DOT,
    ASSIGN,
    OPERATOR,
    LESS,
    GREATER,
    RESULT,
}