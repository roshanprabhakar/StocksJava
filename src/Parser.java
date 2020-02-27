import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Parser {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now;

    public Parser() {
    }

    /**
     * Split string by the colons
     */
    public static HashMap<String, String> getData(String input) {
        HashMap<String, String> map = new HashMap<>();
        ArrayList<Integer> colons = indexOf(input, ":");
        for (Integer i : colons) {

            // left side of colons only describe keys
            int firstQuote = charFromLeft('"', input, i, 2);
            int secondQuote = charFromLeft('"', input, i, 1);
            String key = input.substring(firstQuote + 1, secondQuote);

            // right side of colons are values, either numbers or string surrounded by quotes or null
            /**
             * if (character after colon is quote):
             *  find next quote, substring this selection
             * else return next character to next comma substring
             */
            String value;
            if (input.charAt(i + 1) == '"') {
                int nextQuote = charFromRight('"', input, i + 1, 2);
                value = input.substring(i + 2, nextQuote);
//                System.out.println(value);
            } else { //number, null, false, true, ends in ',' or '}'
                int nextSplice = charFromRight(',', input, i, 1);
                if (nextSplice == -1) {
                    nextSplice = charFromRight('}', input, i, 1);
                }
                value = input.substring(i + 1, nextSplice);
//                System.out.println(value);
            }
            map.put(key, value.replace(",", " "));
        }
        now = LocalDateTime.now();
        map.put("timeStamp", dtf.format(now).replace(",", " "));
        return map;
    }

    private static int charFromRight(char chr, String input, int colonIndex, int rank) {
        int count = 0;
        for (int i = colonIndex; i < input.length(); i++) {
            if (input.charAt(i) == chr) {
                count++;
                if (count == rank) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static int charFromLeft(char chr, String input, int colonIndex, int rank) {
        int count = 0;
        for (int i = colonIndex; i >= 0; i--) { //first character in input is {
            if (input.charAt(i) == chr) {
                count++;
                if (count == rank) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static ArrayList<Integer> indexOf(String string, String oneChrQuery) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(i, i + 1).equals(oneChrQuery)) out.add(i);
        }
        return out;
    }
}
