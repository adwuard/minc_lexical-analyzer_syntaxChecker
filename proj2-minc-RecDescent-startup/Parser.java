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
            this.type = type;
            this.attr = attr;
        }
    }

    public ParserVal yylval;
    Token[]          _tokens;
    int              _lah;
    public Parser(java.io.Reader r) throws java.io.IOException
    {
        this._tokens   = new Token[20];
        this._lah      = 0;

        // read all tokens in advance
        Lexer lex = new Lexer(r, this);
        for(int i=0; i<20; i++)
        {
            int token_type = lex.yylex();
            if (token_type == 0 ) { _tokens[i] = new Token(Parser.ENDMARKER, null); break; } // end of input
            if (token_type == -1) { _tokens[i] = new Token(Parser.ENDMARKER, null); break; } // error

            _tokens[i] = new Token(token_type, yylval);
        }
    }

    public void Advance() throws java.io.IOException
    {
        // increase location of lah
        _lah ++;
    }
    public boolean Match(int token_type) throws java.io.IOException
    {
        if (_lah < 0 || _lah >= 20)
            return false;

        boolean match = (token_type == _tokens[_lah].type);
        _lah ++;
        return match;
    }
    public int GetTokenLocation()
    {
        // get token location for backtracking
        return _lah;
    }
    public void ResetTokenLocation(int loc)
    {
        // restore token location for backtracking
        _lah = loc;
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
        int loc = GetTokenLocation();

        // program -> decl_list
        ResetTokenLocation(loc);
        if(decl_list() && Match(ENDMARKER))
            return true;

        return false;
    }
    public boolean decl_list() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // decl_list -> decl_list'
        ResetTokenLocation(loc);
        if(decl_list_())
            return true;

        return false;
    }
    public boolean decl_list_() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // decl_list'	-> fun_decl decl_list'
        ResetTokenLocation(loc);
        if(fun_decl())
            if(decl_list_())
                return true;

        // decl_list'	-> epsilon
        ResetTokenLocation(loc);
        return true;
    }
    public boolean type_spec() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // type_spec	-> "int"
        ResetTokenLocation(loc);
        if(Match(INT))
            return true;

        return false;
    }
    public boolean fun_decl() throws java.io.IOException, Exception
    {
        int loc = GetTokenLocation();

        // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
        ResetTokenLocation(loc);
        if(type_spec())
            if(Match(IDENT))
                if(Match(LPAREN))
                    if(params())
                        if(Match(RPAREN))
                            if(compound_stmt())
                                return true;

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
