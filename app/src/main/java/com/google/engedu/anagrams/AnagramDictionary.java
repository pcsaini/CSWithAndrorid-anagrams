package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static final String LOG_TAG = "AnagramDict";
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            ArrayList<String> temp_array = new ArrayList<String>();
            ArrayList<String> temp_array1 = new ArrayList<String>();

            if (lettersToWord.containsKey(sortWord(word))){
                temp_array = lettersToWord.get(sortWord(word));
                temp_array.add(word);
            }
            else{
                temp_array.add(word);
                lettersToWord.put(sortWord(word), temp_array);
            }
            if (sizeToWords.containsKey(word.length())){
                sizeToWords.get(word.length());
                temp_array1.add(word);
            }
            else{
                temp_array1.add(word);
                sizeToWords.put(word.length(), temp_array1);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if (!wordSet.contains(word)){
            return false;
        }
        if (word.contains(base)){
            return false;
        }
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = this.sortWord(targetWord);
        for (String s:this.wordList){
            if (targetWord.equals(this.sortWord(s))){
                result.add(s);
                Log.d(LOG_TAG, s);
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String key;
        for (char c = 'a'; c <= 'z'; c++){
            key = sortWord(word + c);
            if (lettersToWord.containsKey(key)){
                Log.d(LOG_TAG,key);
                result.addAll(lettersToWord.get(key));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int maxLength = wordList.size();
        int num = random.nextInt(maxLength), loopIndex;
        String key;
        for (loopIndex = num; loopIndex < (maxLength + num + 1);loopIndex++ ){
            key = sortWord(wordList.get(loopIndex % maxLength));
            if(lettersToWord.containsKey(key)){
                if (lettersToWord.get(key).size() >= MIN_NUM_ANAGRAMS){
                    break;
                }
            }
        }
        return wordList.get(loopIndex % maxLength);
    }

    public String sortWord(String str){
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
