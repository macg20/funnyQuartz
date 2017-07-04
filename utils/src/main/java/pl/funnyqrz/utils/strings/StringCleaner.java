package pl.funnyqrz.utils.strings;

public class StringCleaner {

    public static String deleteCharByPosition(String string, int position) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.deleteCharAt(position);
        return stringBuilder.toString();
    }
}
