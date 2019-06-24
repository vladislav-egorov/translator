package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Rextester {

    private static Map<String, Integer> num = new HashMap<>();
    private static Map<Integer, String> errors = new HashMap<>();
    private static ArrayList<String> words;
    private static ArrayList<Number> numbers = new ArrayList<>();
    private static String message = "";

    static {
        num.put("et", 0);
        num.put("un", 1);
        num.put("deux", 2);
        num.put("trois", 3);
        num.put("quatre", 4);
        num.put("cinq", 5);
        num.put("six", 6);
        num.put("sept", 7);
        num.put("huit", 8);
        num.put("neuf", 9);
        num.put("dix", 10);
        num.put("onze", 11);
        num.put("douze", 12);
        num.put("treize", 13);
        num.put("quatorze", 14);
        num.put("quinze", 15);
        num.put("seize", 16);
        num.put("dix-sept", 17);
        num.put("dix-huit", 18);
        num.put("dix-neuf", 19);
        num.put("vingt", 20);
        num.put("vingts", 20);
        num.put("trente", 30);
        num.put("quarante", 40);
        num.put("cinquante", 50);
        num.put("soixante", 60);
        num.put("cent", 100);
        errors.put(0, "Некорректный ввод");
        errors.put(1, "два числа единичного порядка");
        errors.put(2, "два числа десятого порядка");
        errors.put(3, "два числа сотого порядка");
        errors.put(6, "Пустая строка");
    }

    public static final String translateToNumbers(String str) {
        System.out.println(str);
        if (str.isEmpty()) return errors.get(6);
        while (str.indexOf("-") != -1) {
            str = str.replaceAll("-", " ");
        }
        while (str.indexOf("  ") != -1) {
            str = str.replaceAll("  ", " ");
        }
        if (str.indexOf(",") != -1) str = str.replaceAll(",", "");
        if (str.indexOf(" ") == 0) str = str.replaceFirst(" ", "");
        if (str.lastIndexOf(" ") == str.length() - 1) str = str.substring(0, str.length() - 1);
        if (str.indexOf("vingts") != -1) str = str.replaceAll("vingts", "vingt");
        words = getWords(str);
        if (words.get(0).contains(errors.get(0))) {
            System.out.println(words.get(0));
            return words.toString();
        }
        if (words.contains("et")) words.remove("et");
        numbers = getNumbers(words);
        checkPoliticsNumbers(numbers);
        System.out.println(message);
        return message;
    }

    public static final Integer convert(ArrayList<String> words) {
        Integer i = 0, number = 0;
        if (words.size() > 0) {
            if (words.get(0).equals("cent")) {
                number = translateX00(words.get(0));
                i = 1;
            }
            if (words.size() > 1) {
                if (words.get(1).equals("cent")) {
                    number = translateX00(words.get(0));
                    i = 2;
                }
            }
        }
        switch (words.size() - i) {
            case 1:
                number += translate0XX(words.get(0 + i));
                break;
            case 2:
                number += translate0XX(words.get(0 + i), words.get(1 + i));
                break;
            case 3:
                number += translate0XX(words.get(0 + i), words.get(1 + i), words.get(2 + i));
                break;
            case 4:
                number += translate0XX(words.get(0 + i), words.get(1 + i), words.get(2 + i), words.get(3 + i));
                break;
        }
        return number;
    }

    public static final void checkPoliticsNumbers(ArrayList<Number> numbers) {
        if (numbers.isEmpty()) {
            message = "чисел нет, ыыы";
            return;
        }
        if (numbers.size() == 1) {
            message = numbers.get(0).value.toString();
            return;
        }
        message = "несколько чисел или неправильный порядок";
        if (numbers.size() > 1) {
            for (int i = 0; i < numbers.size(); i++) {
                if (numbers.get(i).value > 19) {
                    int del = numbers.get(i).type.equals(Number.Type.D) ? 10 : 100;
                    if (!(numbers.get(i).value % del == 0)) {
                        numbers.add(i + 1, new Number((numbers.get(i).value % del)));
                        numbers.set(i, new Number((numbers.get(i).value / del) * del));
                    }
                }
            }
        }
        for (int i = 0, j = 1; j < numbers.size(); i++, j++) {
            msgNumbers(i, j, Number.Type.E, Number.Type.E, "два числа единичного формата");
            msgNumbers(i, j, Number.Type.E, Number.Type.D, "число десятичного формата после числа единичного формата");
            msgNumbers(i, j, Number.Type.E, Number.Type.C, "число сотого формата после числа единичного формата");
            msgNumbers(i, j, Number.Type.D, Number.Type.D, "два числа десятичного формата");
            msgNumbers(i, j, Number.Type.D, Number.Type.C, "число сотого формата после числа десятичного формата");
            msgNumbers(i, j, Number.Type.C, Number.Type.C, "два числа сотого формата");
        }
    }

    public static void msgNumbers(int i, int j, Number.Type typeI, Number.Type typeJ, String msg) {
        if (numbers.get(i).type == typeI && numbers.get(j).type == typeJ) {
            if (message.equals("несколько чисел или неправильный порядок"))
                message = msg;
        }
    }

    public static final boolean checkPolitics(ArrayList<String> words) {
        int i = 0;
        if (words.size() > 0) {
            if (words.get(0).equals("cent")) {
                i = 1;
            }
            if (words.size() > 1) {
                if (words.get(1).equals("cent")) {
                    i = 2;
                }
            }
        }
        switch (words.size() - i) {
            case 1:
                return (checkNumber(get0_20(words.get(0 + i)), 1, 16))
                        || (get0_20(words.get(0 + i)) == 20)
                        || (get0_20(words.get(0 + i)) == 30)
                        || (get0_20(words.get(0 + i)) == 40)
                        || (get0_20(words.get(0 + i)) == 50)
                        || (get0_20(words.get(0 + i)) == 60);
            case 2:
                return (checkNumber(get0_20(words.get(0 + i), words.get(1)), 17, 19))
                        || (words.get(0 + i).equals("vingt") && checkNumber(get0_20(words.get(1 + i)), 1, 9))
                        || (words.get(0 + i).equals("trente") && checkNumber(get0_20(words.get(1 + i)), 1, 9))
                        || (words.get(0 + i).equals("quarante") && checkNumber(get0_20(words.get(1 + i)), 1, 9))
                        || (words.get(0 + i).equals("cinquante") && checkNumber(get0_20(words.get(1 + i)), 1, 9))
                        || (words.get(0 + i).equals("soixante") && checkNumber(get0_20(words.get(1 + i)), 1, 16))
                        || (words.get(0 + i).equals("quatre") && (words.get(1 + i).equals("vingts") || words.get(1 + i).equals("vingt")));
            case 3:
                return (words.get(0 + i).equals("soixante") && checkNumber(get0_20(words.get(1 + i), words.get(2 + i)), 17, 19))
                        || (words.get(0 + i).equals("quatre") && words.get(1 + i).equals("vingts"))
                        || (words.get(0 + i).equals("quatre") && checkNumber(get0_20(words.get(2 + i)), 1, 16));
            case 4:
                return words.get(0 + i).equals("quatre") && words.get(1 + i).equals("vingt") && checkNumber(get0_20(words.get(2 + i), words.get(3 + i)), 17, 19);
        }
        switch (i) {
            case 1:
                return words.get(0).equals("cent") && words.size() == 1;
            case 2:
                return checkNumber(get0_20(words.get(0)), 1, 9) && words.get(1).equals("cent") && words.size() == 2;
        }
        return false;
    }

    public static final Integer translateX00(String word) {
        int result = 100;
        if (get0_20(word) > 0 && get0_20(word) < 10) {
            result *= get0_20(word);
        }
        return result;
    }

    public static final Integer translate0XX(String... decimleWords) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(decimleWords));
        int result = 0;
        if (words.size() > 1) {
            switch (words.get(0)) {
                case "quatre":
                    result = 80;
                    break;
                case "soixante":
                    result = 60;
                    break;
                case "cinquante":
                    result = 50;
                    break;
                case "quarante":
                    result = 40;
                    break;
                case "trente":
                    result = 30;
                    break;
                case "vingt":
                    result = 20;
                    break;
                default:
                    result = 0;
                    break;
            }
        }
        if (result != 0) {
            if (result == 80) {
                switch (words.size()) {
                    case 2:
                        result = 80;
                        break;
                    case 3:
                        result += get0_20(words.get(2));
                        break;
                    case 4:
                        result += get0_20(words.get(2), words.get(3));
                        break;
                }
            } else {
                result += (words.size() == 2) ? get0_20(words.get(1)) : get0_20(words.get(1), words.get(2));
            }
        } else {
            result += (words.size() == 1) ? get0_20(words.get(0)) : get0_20(words.get(0), words.get(1));
        }
        return result;
    }

    public static final Integer get0_20(String... words1_20) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(words1_20));
        if (words.size() > 1) {
            if (words.get(0).equals("dix") && checkNumber(get0_20(words.get(1)), 7, 9)) {
                if (num.get("dix-" + words.get(1)) != -1) {
                    return num.get("dix-" + words.get(1));
                }
            }
        } else {
            return num.get(words.get(0));
        }
        return 0;
    }

    public static final boolean checkNumber(Integer num, Integer min, Integer max) {
        return num >= min && num <= max;
    }

    public static final ArrayList<Number> getNumbers(ArrayList<String> words) {
        ArrayList<Number> numbers = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        Integer ii = 0, i = 0;
        while (i < words.size()) {
            numbers.add(new Number(0));
            temp.add(words.get(i));
            while (checkPolitics(temp)) {
                numbers.set(ii, Number.valueOf(convert(temp)));
                if (++i >= words.size()) break;
                temp.add(words.get(i));
            }
            temp.clear();
            ii++;
        }
        return numbers;
    }

    public static final ArrayList<String> getWords(String str) {
        ArrayList<String> words = new ArrayList<>();
        while (!str.equals("")) {
            String word = getWord(str);
            if (checkWord(word)) {
                words.add(word);
                str = str.replaceFirst(word, "");
                if (str.indexOf(" ") == 0) str = str.replaceFirst(" ", "");
            } else {
                words.clear();
                words.add(errors.get(0) + " : в слове \"" + word + "\"");
                return words;
            }
        }
        return words;
    }

    public static final String getWord(String str) {
        if (str.indexOf(" ") != -1)
            return str.substring(0, str.indexOf(" "));
        else
            return str;
    }

    public static final Boolean checkWord(String str) {
        return num.containsKey(str);
    }

    static class Number {
        public Integer value;
        public Type type;

        public Number(Integer value) {
            this.value = value;
            type = value > 0 ? value > 9 ? value > 99 ? Type.C : Type.D : Type.E : null;
        }

        static Number valueOf(Integer val) {
            return new Number(val);
        }

        @Override
        public String toString() {
            return value.toString() + type;
        }

        public enum Type {
            C, D, E
        }
    }
}
