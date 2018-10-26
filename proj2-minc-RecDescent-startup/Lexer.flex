/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj

%{

  public Parser   yyparser;
  public int      lineno;

  public Lexer(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
    this.lineno   = 1;
  }
%}

int        = [0-9]+
identifier = [a-z][0-9]*
newline    = \n
whitespace = [ \t\r]+
comment    = "//".*

%%

"print"                             { yyparser.yylval = new ParserVal(null            ); return Parser.PRINT  ; }
"int"                               { yyparser.yylval = new ParserVal(null            ); return Parser.INT    ; }
"{"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.BEGIN  ; }
"}"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.END    ; }
"("                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LPAREN ; }
")"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.RPAREN ; }
"="                                 { yyparser.yylval = new ParserVal(null            ); return Parser.ASSIGN ; }
"+"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.PLUS   ; }
"*"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MUL    ; }
";"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.SEMI   ; }
{int}                               { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.INT_LIT; }
{identifier}                        { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.IDENT  ; }
{comment}                           { /* skip */ }
{newline}                           { /* skip */ }
{whitespace}                        { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
