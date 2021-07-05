/* INSERT default Users */

INSERT INTO public.profile (profile_id, birth_date, elo, elo_problems, email, first_name, is_in_queue, last_name, lobby_id, password, role, user_name)
values  ('8e720ce2-e58c-4feb-9830-6ff48fc31a03', '2020-10-10', 1000, 1000, 'azerty@azerty.fr', 'azerty', false, 'azerty', null, '$2a$10$KUTG4MMDZJhXLEPGXOHIW.fJSFG1zaLh28nvxl73My9BAAi7s7QMm', 'USER', 'azerty'),
        ('0636affc-30d6-4d52-acdf-acb7be9a862f', '2021-08-04', 1000, 1000, 'qwerty@qwerty.fr', 'qwerty', false, 'qwerty', null, '$2a$10$RDtqxMYk.Mze/QKzsbnXMO.lmF6vvr588NV7j9hSGSBoTWDVG8VI6', 'USER', 'qwerty');

/* INSERT default Exercises */

INSERT INTO public.exercise (exercise_id, code, created_at, description, initial_instructions_count, language, title, updated_at) VALUES ('2d145618-501f-4b54-8498-afa348561843', 'import java.util.*;

public class SevenBoom {
    public static void main(String[] args) {

        ChallengeIntern c = new ChallengeIntern();
        String output = c.performTests();
        System.out.println(output);

        /* Solution
            return Arrays.toString(arr).contains("7") ? "Boom!"
                    : "there is no 7 in the array";
        * */

    }

    private static class ChallengeIntern {
        private final StringBuilder result = new StringBuilder();
        private static int counter = 0;

        public String performTests() {

            String test1 = "{2, 6, 7, 9, 3}";
            String test2 = "{33, 68, 400, 5}";
            String test3 = "{86, 48, 100, 66}";
            String test4 = "{76, 55, 44, 32}";
            String test5 = "{35, 4, 9, 37}";
            performTest(test1, new int[]{2, 6, 7, 9, 3}, "Boom!");
            performTest(test2, new int[]{33, 68, 400, 5}, "there is no 7 in the array");
            performTest(test3, new int[]{86, 48, 100, 66}, "there is no 7 in the array");
            performTest(test4, new int[]{76, 55, 44, 32}, "Boom!");
            performTest(test5, new int[]{35, 4, 9, 37}, "Boom!");
            return result.toString();
        }

        public void performTest(String test, int[] array, String isBoom) {
            if (sevenBoom(array).equals(isBoom)) {
                result.append("test" + ++counter + " : " + test + " " + isBoom + " ==> Ok \n");
            } else {
                throw new RuntimeException("test : " + test + " Pas Ok ");
            }
        }//&&&&&&&

        String sevenBoom(int[] arr) {
            final int DIGIT_TO_FIND = 7;
            int temp1;
            int temp2;
            boolean done = false;
            boolean validation = true;

            while (!done) {
                done = true;

                for (int i = 1; i < arr.length; i++) {
                    if (arr[i - 1] > arr[i]) {
                        temp1 = arr[i - 1];
                        temp2 = arr[i];
                        arr[i - 1] = temp2;
                        arr[i] = temp1;
                    }
                }
                for (int i = 1; i < arr.length; i++) {
                    if (arr[i - 1] > arr[i]) {
                        done = false;
                    }
                }
            }
            for (int i = 0; i < arr.length; i++) {
                int thisNumber = arr[i];
                int thisDigit;
                while (thisNumber != 0) {
                    thisDigit = thisNumber % 10;
                    thisNumber = thisNumber / 10;
                    if (thisDigit == DIGIT_TO_FIND || arr[i] == DIGIT_TO_FIND)
                        return "Boom!";
                }
            }
            String str = "";
            for (int i = 0; i < arr.length; i++) {
                int thisNumber = arr[i];
                int thisDigit;
                while (thisNumber != 0) {
                    thisDigit = thisNumber % 10;
                    thisNumber = thisNumber / 10;
                    if (thisDigit == DIGIT_TO_FIND || arr[i] == DIGIT_TO_FIND)
                        str = "Boom!";
                }
            }
            str = "there is no 7 in the array";
            int num = 7;
            int count = 0;
            if (num > 1) {
                boolean array[] = new boolean[num + 1];
                for (int i = 0; i < num; i++) {
                    array[i] = true;
                }
                for (int i = 2; i * i <= num; i++) {
                    if (array[i] == true) {
                        for (int j = i * 2; j <= num; j += i) {
                            array[j] = false;
                        }
                    }
                }
                for (int i = 2; i <= num; i++) {
                    if (array[i] == true) {
                        count++;
                    }
                }
            }
            for (int i = 1; i < arr.length; i++) {
                if (validation) {
                    if (arr[i - 1] + 1 != arr[i]) {
                        validation = false;
                    }
                }
            }
            return "there is no 7 in the array";
        }//&&&&&&&
    }
}
', '2021-07-04 16:06:35.333924', 'Function that takes an array of numbers and return "Boom!" if the digit 7 appears in the array. Otherwise, return "there is no 7 in the array".', 0, 'Java', 'SevenBoom', null);
INSERT INTO public.exercise (exercise_id, code, created_at, description, initial_instructions_count, language, title, updated_at) VALUES ('ab1e024c-0d36-446a-a23a-0214996b41a6', 'import java.util.*;
class ConsecutiveNumbers {

    public static void main(String[] args) {

        ChallengeIntern c = new ChallengeIntern();
        String output = c.performTests();
        System.out.println(output);

        /* Solution
        public class Challenge {
            public static boolean cons(int[] arr) {
                Arrays.sort(arr);
                return (arr[arr.length - 1] - arr[0]) == (arr.length - 1);
            }
        }*/
    }

    private static class ChallengeIntern {
        private final StringBuilder result = new StringBuilder();
        private static int counter = 0;
        public String performTests() {
            String test1 = "{5, 1, 4, 3, 2}";
            String test2 = "{55, 59, 58, 56, 57}";
            String test3 = "{-3, -2, -1, 1, 0}";
            String test4 = "{5, 1, 4, 3, 2, 8}";
            String test5 = "{5, 6, 7, 8, 9, 9}";
            performTest(test1, new int[]{5, 1, 4, 3, 2}, true);
            performTest(test2, new int[]{55, 59, 58, 56, 57}, true);
            performTest(test3, new int[]{5, 1, 4, 3, 2}, true);
            performTest(test4, new int[]{5, 1, 4, 3, 2, 8}, false);
            performTest(test5, new int[]{5, 6, 7, 8, 9, 9}, false);
            return result.toString();
        }

        public void performTest(String test, int[] array, boolean b) {
            if(consecutive(array) == b){
                result.append("test"+ ++counter + " : " + test + " ==> Ok \n");
            } else {
                result.append("test"+ ++counter + " : " + test + " Pas Ok \n");
            }
        }//&&&&&&&
boolean consecutive(int[] arr) {
    int temp1;
    int temp2;
    boolean done = false;
    boolean validation = true;

    while(!done) {
        done = true;

        for(int i = 1; i < arr.length; i++) {
            if(arr[i - 1] > arr[i]) {
                temp1 = arr[i -1];
                temp2 = arr[i];
                arr[i - 1] = temp2;
                arr[i] = temp1;
            }
        }
        for(int i = 1; i < arr.length; i++) {
            if(arr[i - 1] > arr[i]) {
                done = false;
            }
        }
    }
    final int DIGIT_TO_FIND=7;
    String str = "";
    for (int i=0; i<arr.length; i++)
    {
        int thisNumber=arr[i];
        int thisDigit;
        while (thisNumber != 0)
        {
            thisDigit = thisNumber % 10 ;
            thisNumber = thisNumber / 10;
            if (thisDigit == DIGIT_TO_FIND || arr[i]==DIGIT_TO_FIND)
                str = "Boom!";
        }
    }
    str = "there is no 7 in the array";
    int num = 7;
    int count = 0;
    if (num > 1) {
        boolean array[] = new boolean[num + 1];
        for (int i = 0; i < num; i++) {
            array[i] = true;
        }
        for (int i = 2; i * i <= num; i++) {
            if (array[i] == true) {
                for (int j = i * 2; j <= num; j += i) {
                    array[j] = false;
                }
            }
        }
        for (int i = 2; i <= num; i++) {
            if (array[i] == true) {
                count++;
            }
        }
    }
    for(int i = 1; i < arr.length; i++) {
        if(validation) {
            if(arr[i - 1] + 1 != arr[i]) {
                validation = false;
            }
        }
    }

    return validation;
}
//&&&&&&&
    }
}', '2021-07-04 15:46:15.331244', 'Function that determines whether elements in an array can be re-arranged to form a consecutive list of numbers where each number appears exactly once.', 0, 'Java', 'ConsecutiveNumbers', null);
INSERT INTO public.exercise (exercise_id, code, created_at, description, initial_instructions_count, language, title, updated_at) VALUES ('d41d1f8d-8d8f-44fa-bd89-3018678f208b', 'import java.util.*;
import java.math.*;
import java.text.*;
import java.lang.*;
public class PrimeNumbers {

    public static void main(String[] args) {

        ChallengeIntern c = new ChallengeIntern();
        String output = c.performTests();
        System.out.println(output);
    }

    private static class ChallengeIntern {
        private final StringBuilder stringBuilder = new StringBuilder();
        private static int counter = 0;
        public String performTests() {
            String test1 = "20";
            String test2 = "30";
            String test3 = "100";
            String test4 = "200";
            String test5 = "1000";
            String test6 = "-5";
            String test7 = "66";
            String test8 = "133";
            performTest(test1, 8, 20);
            performTest(test2, 10, 30);
            performTest(test3, 25, 100);
            performTest(test4, 46, 200);
            performTest(test5, 168, 1000);
            performTest(test6, 0, -5);
            performTest(test7, 18, 66);
            performTest(test8, 32, 133);
            return stringBuilder.toString();
        }

        public void performTest(String test, int result, int number) {
            if (primeNumbers(number) == result) {
                stringBuilder.append("test" + ++counter + " : " + test + " ==> Ok \n");
            } else {
                stringBuilder.append("test" + ++counter + " : " + test + " ==> Not Ok \n");
            }
        }//&&&&&&&
public static int primeNumbers(int num) {
    int count = 0;
    boolean isCorrect = true;
    int l = 1;
    String str = "";
    int x = 1;
    int y = 0;
    int num1 = 0;
    int num2 = 0;
    String cur = "";
    boolean X = true;
    boolean ans = false;
    boolean done = false;
    ArrayList<String> ascend = new ArrayList<>();

    while (l <= str.length() / 2) {

        if(str.length() % l == 0) {
            int temp = Integer.parseInt(str.substring(0, l));
            isCorrect = true;

            for (int i = l; i < str.length(); i += l) {
                if (temp + 1 != Integer.parseInt(str.substring(i, i + l))) {
                    isCorrect = false;
                    break;
                }

                temp = Integer.parseInt(str.substring(i, i + l));
            }
        }

        if(isCorrect) {
            break;
        } else {
            l++;
        }
    }
    if (num > 1) {
        boolean arr[] = new boolean[num + 1];
        for (int i = 0; i < num; i++) {
            arr[i] = true;
        }
        for (int i = 2; i * i <= num; i++) {
            if (arr[i] == true) {
                for (int j = i * 2; j <= num; j += i) {
                    arr[j] = false;
                }
            }
        }
        for (int i = 2; i <= num; i++) {
            if (arr[i] == true) {
                count++;
            }
        }
        while(!done) {
            ascend.clear();
            y = 0;
            if(str.length() % x == 0) {
                while(str.length() / x > y-(x)) {
                    try {
                        cur = str.substring(y, y+x);
                        ascend.add(cur);
                        y += x;
                    }
                    catch (Exception e) {
                        break;
                    }
                }
                y = 0;
                while(ascend.size()-1 > y) {
                    num1 = Integer.parseInt(ascend.get(y));
                    num2 = Integer.parseInt(ascend.get(y+1));
                    if(num1 == num2-1) {
                        y++;
                    } else {
                        break;
                    }
                }
                if(ascend.size() == y+1) {
                    ans = true;
                    done = true;
                }
            }
            x++;
            if(2 * x > str.length()) {
                done = true;
            }
        }
    }
    return count;
}//&&&&&&&
    }
}
', '2021-07-04 16:15:09.222443', 'Function that finds how many prime numbers there are, up to the given integer.', 0, 'Java', 'PrimeNumbers', null);
