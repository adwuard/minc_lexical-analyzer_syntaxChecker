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
//identifier = ([a-zA-Z_][0-9])+
newline    = \n
whitespace = [ \t\r]+
comment    = "//".*

identifier = [a-zA-Z][a-zA-Z0-9]*

%%

"print"                             { yyparser.yylval = new ParserVal(null            ); return Parser.PRINT      ; }
"bool"                              { yyparser.yylval = new ParserVal(null            ); return Parser.BOOL       ; }
"int"                               { yyparser.yylval = new ParserVal(null            ); return Parser.INT        ; }
"float"                             { yyparser.yylval = new ParserVal(null            ); return Parser.FLOAT      ; }
"record"                            { yyparser.yylval = new ParserVal(null            ); return Parser.RECORD     ; }
"size"                              { yyparser.yylval = new ParserVal(null            ); return Parser.SIZE       ; }
"new"                               { yyparser.yylval = new ParserVal(null            ); return Parser.NEW        ; }
"while"                             { yyparser.yylval = new ParserVal(null            ); return Parser.WHILE      ; }
"if"                                { yyparser.yylval = new ParserVal(null            ); return Parser.IF         ; }
"then"                              { yyparser.yylval = new ParserVal(null            ); return Parser.THEN       ; }
"else"                              { yyparser.yylval = new ParserVal(null            ); return Parser.ELSE       ; }
"return"                            { yyparser.yylval = new ParserVal(null            ); return Parser.RETURN     ; }
"break"                             { yyparser.yylval = new ParserVal(null            ); return Parser.BREAK      ; }
"continue"                          { yyparser.yylval = new ParserVal(null            ); return Parser.CONTINUE   ; }
"&&"                                { yyparser.yylval = new ParserVal(null            ); return Parser.AND        ; }
"||"                                { yyparser.yylval = new ParserVal(null            ); return Parser.OR         ; }
"!"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.NOT        ; }

"{"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.BEGIN      ; }
"}"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.END        ; }
"&"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.ADDR       ; }//pointer
"("                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LPAREN     ; }
")"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.RPAREN     ; }
"["                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LBRACKET   ; }
"]"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.RBRACKET   ; }
"="                                 { yyparser.yylval = new ParserVal(null            ); return Parser.ASSIGN     ; }
">"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.GT         ; }
"<"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.LT         ; }

"+"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.PLUS       ; }
"-"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MINUS      ; }
"*"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MUL        ; }
"/"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.DIV        ; }
"%"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.MOD        ; }

";"                                 { yyparser.yylval = new ParserVal(null            ); return Parser.SEMI       ; }
","                                 { yyparser.yylval = new ParserVal(null            ); return Parser.COMMA      ; }
"."                                 { yyparser.yylval = new ParserVal(null            ); return Parser.DOT        ; }
"=="                                { yyparser.yylval = new ParserVal(null            ); return Parser.EQ         ; }
"!="                                { yyparser.yylval = new ParserVal(null            ); return Parser.NE         ; }
"<="                                { yyparser.yylval = new ParserVal(null            ); return Parser.LE         ; }
">="                                { yyparser.yylval = new ParserVal(null            ); return Parser.GE         ; }

{identifier}                        { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.IDENT      ; }
{int}                               { yyparser.yylval = new ParserVal((Object)yytext()); return Parser.INT_LIT    ; }


{comment}                           { /* skip */ }
{newline}                           { /* skip */ }
{whitespace}                        { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
