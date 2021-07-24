package fr.esgi.projetannuel.service.code;

public class CCodeWrapper {
    private CCodeWrapper() {
    }

    public static final String HEADER = "#include<stdio.h>\n" +
            "#include<string.h>\n" +
            "//&&&&&&&\n";
    public static final String BODY = "\n//&&&&&&&\n" +
            "int main( )               \n" +
            "{    \n" +
            "    char result[400] =\"\";\n";
    public static final String FOOTER = "\nprintf(\"%s\\n\",result);\n" +
            "}";
}
