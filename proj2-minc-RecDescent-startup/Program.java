public class Program {
	public static void main(String[] args) throws Exception
    {
        //java.io.Reader r = new java.io.StringReader
        //("int main(int a)\n"
        //+"{\n"
        //+"    int x;\n"
        //+"    int y;\n"
        //+"    x = x + 1;\n"
        //+"    y == x + 1;\n"
        //+"    // abce \n"
        //+"}\n"
        //);

        if(args.length <= 0)
            return;
        java.io.Reader r = new java.io.FileReader(args[0]);

        Parser p = new Parser(r);
        p.yyparse();
	}
}
