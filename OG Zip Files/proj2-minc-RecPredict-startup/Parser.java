public class Parser
{
    public static final int ENDMARKER   =  0;
    public static final int LEXERROR    =  1;

    public static final int PRINT       = 11;
    public static final int BOOL        = 12;
    public static final int INT         = 13;
    public static final int FLOAT       = 14;
    public static final int RECORD      = 15;
    public static final int SIZE        = 17;
    public static final int NEW         = 18;
    public static final int WHILE       = 19;
    public static final int IF          = 20;
    public static final int THEN        = 21;
    public static final int ELSE        = 22;
    public static final int RETURN      = 23;
    public static final int BREAK       = 24;
    public static final int CONTINUE    = 25;
    public static final int AND         = 27;
    public static final int OR          = 28;
    public static final int NOT         = 29;
    public static final int BEGIN       = 30;
    public static final int END         = 31;
    public static final int ADDR        = 32;
    public static final int LPAREN      = 33;
    public static final int RPAREN      = 34;
    public static final int LBRACKET    = 35;
    public static final int RBRACKET    = 37;
    public static final int ASSIGN      = 38;
    public static final int GT          = 39;
    public static final int LT          = 40;
    public static final int PLUS        = 41;
    public static final int MINUS       = 42;
    public static final int MUL         = 43;
    public static final int DIV         = 44;
    public static final int MOD         = 45;
    public static final int SEMI        = 47;
    public static final int COMMA       = 48;
    public static final int DOT         = 49;
    public static final int EQ          = 50;
    public static final int NE          = 51;
    public static final int LE          = 52;
    public static final int GE          = 53;
    public static final int BOOL_LIT    = 54;
    public static final int INT_LIT     = 55;
    public static final int FLOAT_LIT   = 57;
    public static final int IDENT       = 58;

    public class Token {
        public int       type;
        public ParserVal attr;
        public Token(int type, ParserVal attr) {
            this.type   = type;
            this.attr   = attr;
        }
    }

    public ParserVal yylval;
    Token _token;
    Lexer _lexer;
    public Parser(java.io.Reader r) throws java.io.IOException
    {
        _lexer = new Lexer(r, this);
        _token = null;
        Advance();
    }
    
    public void Advance() throws java.io.IOException
    {
        int token_type = _lexer.yylex();

        if(token_type ==  0)      _token = new Token(ENDMARKER , null  );
        else if(token_type == -1) _token = new Token(LEXERROR  , yylval);
        else                      _token = new Token(token_type, yylval);
    }

    public boolean Match(int token_type) throws java.io.IOException
    {
        boolean match = (token_type == _token.type);

        if(_token.type != ENDMARKER)
            Advance();

        return match;
    }

    public int yyparse() throws java.io.IOException, Exception
    {
        parse();
        return 0;
    }

    public void parse() throws java.io.IOException, Exception
    {
        boolean successparse = true;
        if(successparse)
            System.out.println("Success: no syntax error found.");
        else
            System.out.println("Error: there exist syntax errors.");
    }

    public boolean program() throws java.io.IOException, Exception
    {
        switch(_token.type)
        {
            // program -> decl_list
            case INT:
            case ENDMARKER:
                if( decl_list()      == false) return false;
                if( Match(ENDMARKER) == false) return false;
                return true;
        }
        return false;
    }
    public boolean decl_list() throws java.io.IOException, Exception
    {
        switch(_token.type)
        {
            // decl_list -> decl_list'
            case ENDMARKER:
            case INT:
                if( decl_list_() == false) return false;
                return true;
        }
        return false;
    }
    public boolean decl_list_() throws java.io.IOException, Exception
    {
        switch(_token.type)
        {
            // decl_list'	-> fun_decl decl_list'
            case INT:
                if(fun_decl()   == false) return false;
                if(decl_list_() == false) return false;
                return true;
            // decl_list'	-> epsilon
            case ENDMARKER:
                return true;
        }
        return false;
    }
    public boolean type_spec() throws java.io.IOException, Exception
    {
        switch(_token.type)
        {
            // type_spec	-> "int"
            case INT:
                if( Match(INT) == false) return false;
                return true;
        }
        return false;
    }
    public boolean fun_decl() throws java.io.IOException, Exception
    {
        switch(_token.type)
        {
            // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
            case INT:
                if( type_spec()     == false) return false;
                if( Match(IDENT)    == false) return false;
                if( Match(LPAREN)   == false) return false;
                if( params()        == false) return false;
                if( Match(RPAREN)   == false) return false;
                if( compound_stmt() == false) return false;
                return true;
        }
        return false;
    }
    public boolean params() throws java.io.IOException, Exception
    {
        throw new Exception("not implemented");
    }
    public boolean compound_stmt() throws java.io.IOException, Exception
    {
        throw new Exception("not implemented");
    }
}
