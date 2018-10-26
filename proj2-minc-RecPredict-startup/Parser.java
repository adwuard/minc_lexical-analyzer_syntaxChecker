import javax.print.attribute.*;

public class Parser
{
    public static final int ENDMARKER   =  0;
    public static final int LEXERROR    =  1;
    
    public static final int PRINT       = 11; // print
    public static final int BOOL        = 12; // bool
    public static final int INT         = 13; // int
    public static final int FLOAT       = 14; // float
    public static final int RECORD      = 15; // record
    public static final int SIZE        = 17; // size
    public static final int NEW         = 18; // new
    public static final int WHILE       = 19; // while
    public static final int IF          = 20; // if
    public static final int THEN        = 21; // then
    public static final int ELSE        = 22; // else
    public static final int RETURN      = 23; // return
    public static final int BREAK       = 24; // break
    public static final int CONTINUE    = 25; // continue
    public static final int AND         = 27; // &&
    public static final int OR          = 28; // ||
    public static final int NOT         = 29; // !
    public static final int BEGIN       = 30; // {
    public static final int END         = 31; // }
    public static final int ADDR        = 32; // &
    public static final int LPAREN      = 33; // (
    public static final int RPAREN      = 34; // )
    public static final int LBRACKET    = 35; // [
    public static final int RBRACKET    = 37; // ]
    public static final int ASSIGN      = 38; // =
    public static final int GT          = 39; // >
    public static final int LT          = 40; // <
    public static final int PLUS        = 41; // +
    public static final int MINUS       = 42; // -
    public static final int MUL         = 43; // *
    public static final int DIV         = 44; // /
    public static final int MOD         = 45; // %
    public static final int SEMI        = 47; // ;
    public static final int COMMA       = 48; // ,
    public static final int DOT         = 49; // .
    public static final int EQ          = 50; // ==
    public static final int NE          = 51; // !=
    public static final int LE          = 52; // <=
    public static final int GE          = 53; // >=
    public static final int BOOL_LIT    = 54; // bool pass back
    public static final int INT_LIT     = 55; // int pass back
    public static final int FLOAT_LIT   = 57; // float pass back
    public static final int IDENT       = 58; // identifiers
    
//    public static final boolean DebugFlag = true;
    public static final boolean DebugFlag = false; 
    //Strings maybe?

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

        if(token_type ==  0)      _token = new Token(ENDMARKER , null  ); //advance until reached end 
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
        boolean successparse = program();
        if(successparse)
            System.out.println("Success: no syntax error found.");
        else
            System.out.println("Error: there exist syntax errors.");
    }
    
    
    
    //Grammar Start from here
    //_token.type respond value of the token
    public boolean program() throws java.io.IOException, Exception
    {   if(DebugFlag)System.out.println("program");
        switch(_token.type)
        {
            // program -> decl_list
            case INT:
                if( decl_list()      == false) return false;
                return true;
            
            case ENDMARKER:
                if(DebugFlag)System.out.println("End Marker");
                if( decl_list()      == false) return false;
                //if( Match(ENDMARKER) == false) return false;
                return true;
        }
        return false;
    }
    
    
    //Done
    public boolean decl_list() throws java.io.IOException, Exception
    {   if(DebugFlag)System.out.println("decl_list");
        switch(_token.type)
        {
            // decl_list -> decl_list'
            case ENDMARKER:
                if( decl_list_()      == false) return false;
                //if( Match(ENDMARKER) == false) return false;
                return true;
            
            case INT:
                if( decl_list_() == false) return false;
                return true;
        }
        return false;
    }
    
    
    //Done
    public boolean type_spec() throws java.io.IOException, Exception
    {    
        if(DebugFlag)System.out.println("type_spec");
        switch(_token.type)
        {
            // type_spec	-> "int"
            case INT:
                if(Match(INT) == false) return false;
                if(DebugFlag)System.out.println(" ->type_spec "+"\t\t\t INT Matched");
                return true;
        }
        return false;
    }
    
    
    //Done
    public boolean fun_decl() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("fun_decl");
        switch(_token.type)
        {
            // fun_decl	-> type_spec IDENT "(" params ")" compound_stmt
            case INT:
                if( type_spec()     == false) return false;
                //System.out.println("Matched"+_token.attr +_token.type);

                if( Match(IDENT)    == false) return false;
                if(DebugFlag)System.out.println(" ->fun_decl "+"\t\t\tIDENT Matched");
                if( Match(LPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->fun_decl "+"\t\t\t( Matched");
                if( params()        == false) return false;
                if( Match(RPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->fun_decl "+"\t\t\t) Matched");
                if( compound_stmt() == false) return false;
                return true;
        }
        return false;
    }
    
    
    //-Maybe
    public boolean params() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("params");
        //params -> param_list
//        switch(_token.type)
//        {
//            case INT:
//                if( param_list()     == false) return false;
//                return true;
//            case COMMA:
//                if( param_list()     == false) return false;
//                return true;
//            
//             case ENDMARKER:
//                if(DebugFlag)System.out.println("End Marker");
//                if( param_list()      == false) return false;
//                //if( Match(ENDMARKER) == false) return false;
//                return true;
//        }
        if(param_list() == false) return false;
        return true;
    }
    
    
    public boolean param_list() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("param_list");
         //param_list-> param param_list'
         switch(_token.type)
            {
                case INT:
                    if( param()     == false) return false;
                    if( param_list_()     == false) return false;
                    return true;
                    
                case COMMA:
                    if( param()           == false) return false;
                    if( param_list_()     == false) return false;
                    return true;
            
            }
            return false;
    }
    
    //Done
    public boolean param() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("param");
            //param -> type_spec IDENT
            switch(_token.type){
                case INT:
                    if( type_spec()     == false) return false;
                    if( Match(IDENT)    == false) return false;
                    if(DebugFlag)System.out.println(" ->param "+"\t\t\tIDENT Matched");
                    return true;
            }
            return false;
    }

   
    public boolean stmt_list() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("stmt_list");
         // stmt_list -> stmt_list'
//        switch(_token.type){
//            case IDENT:
//                if( stmt_list_()    == false) return false;
//                return true;
//            
//            case SEMI:
//                if( stmt_list_()    == false) return false;
//                return true;
//            
//            case BEGIN:
//                if( stmt_list_()    == false) return false;
//                return true;
//                
//            case IF:
//                if( stmt_list_()    == false) return false;
//                return true;
//            
//            case WHILE:
//                if( stmt_list_()    == false) return false;
//                return true;
//                    
//            case ENDMARKER:
//                if( stmt_list_()      == false) return false;
//                //if( Match(ENDMARKER)  == false) return false;
//                return true;
//                
//            default: return stmt_list_();
//      }
        if( stmt_list_()    == false) return false;
        return true;
    }
    
    
    public boolean stmt() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("stmt");
         // stmt -> expr_stmt | compound_stmt | if_stmt | while_stmt
        switch(_token.type){
            case IDENT:
                if( expr_stmt()     == false) return false;
                return true;
            
            case SEMI:
                if( expr_stmt()     == false) return false;
                return true;
            
            case BEGIN:
                if( compound_stmt()    == false) return false;
                return true;
                    
            case IF:
                if( if_stmt()     == false) return false;
                return true;
            
            case WHILE:
                if( while_stmt()   == false) return false;
                return true;

            case ENDMARKER:
                if(DebugFlag)System.out.println("End Marker");
                if( expr_stmt()    == false) return false;
                if( compound_stmt()    == false) return false;
                if( if_stmt()    == false) return false;
                if( while_stmt()    == false) return false;
                //if( Match(ENDMARKER)  == false) return false;
                
                return true;
        }
        return false;
    }
    
    
    public boolean expr_stmt() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("expr_stmt");
         // expr_stmt -> IDENT "=" expr ";" | ";"
        switch(_token.type){
            case IDENT:
                if( Match(IDENT)     == false) return false;
                if(DebugFlag)System.out.println(" ->expr_stmt "+"\t\t\tIDENT Matched");
                if( Match(ASSIGN)     == false) return false;
                if(DebugFlag)System.out.println(" ->expr_stmt "+"\t\t\t= Matched");
                if( expr()     == false) return false;
                if( Match(SEMI)     == false) return false;
                if(DebugFlag)System.out.println(" ->expr_stmt "+"\t\t\t; Matched");
                return true;
            
            case SEMI:
                if( Match(SEMI)     == false) return false;
                if(DebugFlag)System.out.println(" ->expr_stmt "+"\t\t\tSEMI Matched");
                return true;
                
        }
        return false;
    }
    
    
    public boolean while_stmt() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("while_stmt");
         // while_stmt -> "while" "(" expr ")" stmt
        switch(_token.type){
            case WHILE:
                if( Match(WHILE)     == false) return false;
                if(DebugFlag)System.out.println(" ->while_stmt "+"\t\t\tWhile Matched");
                if( Match(LPAREN)    == false) return false;
                if(DebugFlag)System.out.println(" ->while_stmt "+"\t\t\t( Matched");
                if( expr()           == false) return false;
                if( Match(RPAREN)     == false) return false;
                if(DebugFlag)System.out.println(" ->while_stmt "+"\t\t\t) Matched");
                if( stmt()           == false) return false;
                return true;
        }
        return false;
    }
    
    public boolean compound_stmt() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("compound_stmt");
        // compound_stmt ->  "{"local_decls stmt_list "}"
        switch(_token.type){
                case BEGIN:  
                    if( Match(BEGIN)     == false) return false;
                    if(DebugFlag)System.out.println(" ->compound_stmt "+"\t\t\t{ Matched");
                    if( local_decls()    == false) return false;
                    if( stmt_list()           == false) return false;
                    if( Match(END)     == false) return false;
                    if(DebugFlag)System.out.println(" ->compound_stmt "+"\t\t\t} Matched");
                    return true;
            }
            return false;    
    }


    public boolean local_decls() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("local_decls");
         // local_decls -> local_decls'
        
        if( local_decls_()    == false) return false;
            
        
        return true;
    }
    
    
    public boolean local_decl() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("local_decl");
         // local_decl -> type_spec IDENT ";"
        switch(_token.type){
            case INT:
                if(type_spec()    == false) return false;
                if( Match(IDENT)  == false) return false;
                if(DebugFlag)System.out.println(" ->local_decl "+"\t\t\tIdent Matched");
                if( Match(SEMI)  == false) return false;
                if(DebugFlag)System.out.println(" ->local_decl "+"\t\t\t; Matched");
                return true;
        }
        return false;
    }
    
    public boolean if_stmt() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("if_stmt");
         // if_stmt -> "if" "(" expr ")" stmt "else" stmt
        switch(_token.type){
            case IF:
                if( Match(IF)       == false) return false;
                if(DebugFlag)System.out.println(" ->if_stmt "+"\t\t\tif Matched");
                if( Match(LPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->if_stmt "+"\t\t\t( Matched");
                if( expr()          == false) return false;
                if( Match(RPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->if_stmt "+"\t\t\t) Matched");
                if( stmt()          == false) return false;
                if( Match(ELSE)     == false) return false;
                if(DebugFlag)System.out.println(" ->if_stmt "+"\t\t\telse Matched");
                if( stmt()          == false) return false;
                return true;
        }
        return false;
    }
    
    
    public boolean arg_list() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("arg_list");
         // arg_list -> expr arg_list'
        switch(_token.type){
            case IDENT:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;
            
            case LPAREN:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;
                
            case INT_LIT:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;

        }
        return false;
    }
    
    
    public boolean args() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("args");
         // args -> expr arg_list' | epsilon
        switch(_token.type){
            
            case IDENT:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;
            
            case LPAREN:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;
                
            case INT_LIT:
                if( expr()       == false) return false;
                if( arg_list_()  == false) return false;
                return true;
            
            case ENDMARKER:
                if( expr()          == false) return false;
                if( arg_list_()     == false) return false;
                if( Match(ENDMARKER)  == false) return false;
                if(DebugFlag)System.out.println("End Marker");
                return true;
        }
        return true;
    }
    
    
    public boolean expr() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("expr");
         //expr -> term expr'
//        switch(_token.type){
//            case IDENT:
//                if( term()   == false) return false;
//                if( expr_()  == false) return false;
//                return true;
//                
//            case LPAREN:
//                if( term()       == false) return false;
//                if( expr_()  == false) return false;
//                return true;
//                
//            case INT_LIT:
//                if( term()       == false) return false;
//                if( expr_()  == false) return false;
//                return true;
//            
//        }
        if( term()       == false) return false;
        if( expr_()  == false) return false;
        return true;
    }
    
    
    public boolean term() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("term");
         //term -> factor term'
//        switch(_token.type){
//            case IDENT:
//                if( factor()       == false) return false;
//                if( term_()  == false) return false;
//                return true;
//                    
//            case LPAREN:
//                if( factor()       == false) return false;
//                if( term_()  == false) return false;
//                return true;
//                
//            case INT_LIT:
//                if( factor()       == false) return false;
//                if( term_()  == false) return false;
//                return true;
//            
//        }
        
        if( factor()       == false) return false;
        if( term_()  == false) return false;
        return true;
    }
    
    
    public boolean factor() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("factor");
         // factor -> IDENT factor' | "(" expr ")" | INT_LIT
        switch(_token.type){
            case IDENT:
                if( Match(IDENT)       == false) return false;
                if(DebugFlag)System.out.println(" ->factor "+"\t\t\tIDENT Matched");
                if( factor_()          == false) return false;
                return true;
                    
            case LPAREN:
                if( Match(LPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->factor "+"\t\t\t( Matched");
                if( expr()           == false) return false;
                if( Match(RPAREN)   == false) return false;
                if(DebugFlag)System.out.println(" ->factor "+"\t\t\t) Matched");
                return true;
                
            case INT_LIT:
                if( Match(INT_LIT)   == false) return false;
                if(DebugFlag)System.out.println(" ->factor "+"\t\t\tINT_LIT Matched");
                return true;
        }
        return false;
            
    }
    
    
    public boolean decl_list_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("decl_list_");
        // decl_list'	-> fun_decl decl_list' | epsilon
        switch(_token.type){
                case INT:
                    if(fun_decl()   == false) return false;
                    if(decl_list_() == false) return false;
                    return true;
            
                // decl_list'	-> epsilon
                case ENDMARKER:
                if(DebugFlag)System.out.println("End Marker");
                    return true;
            }
            return false;
    }
    
    public boolean param_list_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("param_list_");
         // param_list_ -> "," param param_list' | epsilon
        switch(_token.type){
             case COMMA:
                    if(Match(COMMA) == false) return false;
                    if(DebugFlag)System.out.println(" ->param_list_ "+"\t\t\t, Matched");
                    if(param()   == false) return false; //int a
                    if(param_list_() == false) return false;
                    return true;
                    
            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
            
        }
        return true;
    }
    
    
    public boolean stmt_list_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("stmt_list_");
         // stmt_list_ -> stmt  stmt_list'  |  epsilon
        switch(_token.type){
            case IDENT:
                if(DebugFlag)System.out.println("IDNT");
                if(stmt()   == false) return false;
                if(stmt_list_() == false) return false;
                return true;
            
            case SEMI: 
                if(stmt()   == false) return false;
                if(stmt_list_() == false) return false;
                return true;
                    
                     
            case BEGIN:
                if( compound_stmt()    == false) return false;
                return true;
                    
            case IF:
                if( if_stmt()     == false) return false;
                return true;
            
            case WHILE:
                if( while_stmt()   == false) return false;
                return true;

            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
        }
        return true;
    }
    
    
    public boolean local_decls_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("local_decls_");
         // local_decls_ -> local_decl local_decls' | epsilon
        switch(_token.type){
        
            case INT:
                if(local_decl()   == false) return false;
                if(local_decls_() == false) return false;
                return true;
        
            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
                
        }
        return true;
    }
    
    
    public boolean arg_list_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("arg_list_");
         // arg_list' -> "," expr arg_list' | epsilon
            switch(_token.type){
                    case COMMA:
                        if(Match(COMMA)== false) return false;
                        if(DebugFlag)System.out.println(" ->arg_list_ "+"\t\t\t, Matched");
                        if(expr()      == false) return false;
                        if(arg_list_() == false) return false;
                        return true;
                
                    // arg_list' -> epsilon
                    case ENDMARKER:
                    if(DebugFlag)System.out.println("End Marker");
                        return true;
            }
            return true;
    }
    
    
    public boolean expr_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("expr_");
        // expr_ -> "+" term expr' | epsilon
        switch(_token.type){
           
            case PLUS:
                if(Match(PLUS)  == false) return false;
                if(DebugFlag)System.out.println(" ->expr_ "+"\t\t\t+ Matched");
                if(term()      == false) return false;
                if(expr_()      == false) return false;
                return true;
            
            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
        }
        return true;
    }
    
    
    public boolean term_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("term_");
        // term_ ->  "==" factor term' | "*" factor term' | epsilon
        switch(_token.type)
        {
            case EQ:
                if(Match(EQ)    == false) return false;
                if(DebugFlag)System.out.println(" ->term_ "+"\t\t\t== Matched");
                if(factor()      == false) return false;
                if(term_()      == false) return false;
                return true;
            
            case MUL:
                if(Match(MUL)    == false) return false;
                if(DebugFlag)System.out.println(" ->term_ "+"\t\t\t* Matched");
                if(factor()      == false) return false;
                if(term_()      == false) return false;
                return true;
            
            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
            
        }
        return true;
    }
    
    //Checked
    public boolean factor_() throws java.io.IOException, Exception
    {
        if(DebugFlag)System.out.println("factor_");
        // factor_ -> "("args")" | epsilon
        switch(_token.type){
            case LPAREN:
                if(Match(LPAREN)    == false) return false;
                if(DebugFlag)System.out.println(" ->factor_ "+"\t\t\t( Matched");
                if(args()           == false) return false;
                if(Match(RPAREN)    == false) return false;
                if(DebugFlag)System.out.println(" ->factor_ "+"\t\t\t) Matched");
                return true;
            
            case ENDMARKER:
            if(DebugFlag)System.out.println("End Marker");
                return true;
            
        }
        return true;
    }


}
