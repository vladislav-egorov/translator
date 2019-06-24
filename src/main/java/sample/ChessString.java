package sample;

public class ChessString {

    public static String chessConverter(String s1, String s2) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] s1split = s1.split(" ");
        String[] s2split = s2.split(" ");
        int maxLength = s1split.length > s2split.length ? s1split.length : s2split.length;

        for (int i = 0; i < maxLength; i++) {
            if (i <= s1split.length - 1) {
                stringBuilder.append(s1split[i]);
                stringBuilder.append(" ");
            }
            if (i <= s2split.length - 1) {
                stringBuilder.append(s2split[i]);
                stringBuilder.append(" ");
            }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String s1 = "1111 1111 1111 1111 1111 1111 1111 1111";
        String s2 = "2222 2222 2222 2222";
        System.out.println(chessConverter(s1, s2));
    }
}
