package fr.esgi.projetannuel.model;

public class Constants {
    private Constants() {
    }

    public static final String EXERCISE = "Exercise";
    public static final String COMPILER_BASE_URL = "http://52.209.183.45:8080/api/compiler";
    public static final String USER = "user";

    public static final String CLASS_NAME = "ClassName";
    public static final String CLASS_MAIN_BUILDER = "import java.util.*;\n" +
            "import java.math.*;\n" +
            "import java.text.*;\n" +
            "import java.lang.*;\n" +
            "public class ClassName {\n" +
            "    public static void main(String[] args) {\n" +
            "\n" +
            "        ChallengeIntern c = new ChallengeIntern();\n" +
            "        String output = c.performTests();\n" +
            "        System.out.println(output);\n" +
            "\n" +
            "    }\n";
    public static final String INNER_CLASS = "private static class ChallengeIntern {\n" +
            "        private final StringBuilder displayableOutput = new StringBuilder();\n" +
            "        public String performTests() {\n";
    public static final String RETURN_OUTPUT = "\nreturn displayableOutput.toString();\n" +
            "        }\n";
    public static final String METHOD_MODIFIER_VISIBILITY = "public static\n" +
            "//&&&&&&&\n";
    public static final String CLOSE_BUILDING = "//&&&&&&&\n" +
            "    }\n" +
            "}";
}
