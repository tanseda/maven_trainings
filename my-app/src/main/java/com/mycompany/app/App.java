package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Hello world!
 */
public class App {
    public static class MyException extends Exception {
        MyException(String s) {
            super(s);
        }
    }

    public static class FormattedText {
        long start;
        long end;
        String text;

        FormattedText(long start, long end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }

        @Override
        public String toString() {
            return start + " - " + end + " - " + text;
        }
    }

    public static void main(String[] args) {
//        System.out.println("Answer: " + new App().decodeAtIndex("leet2code3", 10));
        System.out.println("Answer: " + new App().decodeAtIndex("vzpp636m8y", 2920));
//        System.out.println("Answer: " + new App().decodeAtIndex("ha22", 5));
//        System.out.println("Answer: " + new App().decodeAtIndex("abc", 1));
//        System.out.println("Answer: " + new App().decodeAtIndex("a3bc", 4));
//        System.out.println("Answer: " + new App().decodeAtIndex("a2345678999999999999999", 1));
//        System.out.println("Answer: " + new App().decodeAtIndex("y959q969u3hb22odq595" ,  222280369));
    }

    public String decodeAtIndex(String S, int K) {
        K--;
        StringBuilder code = new StringBuilder();
        char[] inputCharacters = S.toCharArray();

        List<FormattedText> list = new ArrayList<FormattedText>();

        for (int i = 0; i < inputCharacters.length; i++) {
            if (Character.isDigit(inputCharacters[i])) {
                String repeatadText = code.toString();
                code = new StringBuilder();

                long repeatCount = Character.getNumericValue(inputCharacters[i]);
                while (inputCharacters.length > i + 1 && Character.isDigit(inputCharacters[i + 1])) {
                    repeatCount *= 10;
                    repeatCount += Character.getNumericValue(inputCharacters[++i]);
                }

                try {
                    add(list, repeatadText, repeatCount, K);
                } catch (MyException e) {
                    return e.getMessage();
                }
            } else {
                code.append(inputCharacters[i]);
            }
        }

        String s = code.toString();
        if (!s.isEmpty()) {
            try {
                add(list, s, 1, K);
            } catch (MyException e) {
                return e.getMessage();
            }
        }

        return "";
    }

    void add(List<FormattedText> list, String text, long count, int K) throws MyException {
        if (list.isEmpty()) {
            list.add(createFormattedText(0, text.length() * count - 1, text, K));
        }
        else {
            long curIndex = list.get(list.size() - 1).end + 1;
            list.add(createFormattedText(curIndex, curIndex + text.length() - 1, text, K));
            curIndex += text.length();

            FormattedText[] allElements = list.toArray(new FormattedText[list.size()]);

            for (long i = 1; i < count; i++) {
                long toAdd = curIndex * i;
                for (FormattedText element : allElements) {
                    list.add(createFormattedText(element.start + toAdd, element.end + toAdd, element.text, K));
                }
            }
        }
    }

    FormattedText createFormattedText (long start, long end, String text, int K) throws MyException {
        if (start <= K && K <= end) {
            System.out.println(start + " - " + end + " - " + text + " - " + K);
            int charAt = ((int) (K - start)) % text.length();
            throw new MyException(String.valueOf(text.charAt(charAt)));
        }
        return  new FormattedText(start, end, text);
    }
}
