package fr.esgi.projetannuel.service.code;

public class PythonCodeWrapper {
    private PythonCodeWrapper(){}

    public static final String HEADER = "import dis\n" +
            "import types\n" +
            "##&&&&&&\n";
    public static final String BODY = "##&&&&&&\n" +
            "def main():\n" +
            "  \tresult = \"\"\n";
    public static final String FOOTER =
            "  \tcounter = 0\n" +
            "  \tbyteCode = dis.Bytecode(challenge)\n" +
            "  \tfor instr in byteCode:\n" +
            "  \tcounter = counter + 1\n" +
            "  \tprint(\"&&&lines&&&\",counter, \"&&&count&&&\")\n" +
            "  \tresult+=\"\\n\"\n" +
            "  \tprint()\n" +
            "  \tprint(result)\n" +
            "main()";

}
