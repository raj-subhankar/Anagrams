package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 2;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private HashSet<String> wordSet = new HashSet<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeTowords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String sortedWord = sortString(word);

            int wordlen = word.length();
            if(!sizeTowords.containsKey(wordlen)){
                sizeTowords.put(wordlen, new ArrayList<String>());
            }
            sizeTowords.get(wordlen).add(word);

            if(!lettersToWord.containsKey(sortedWord)){
                lettersToWord.put(sortedWord, new ArrayList<String>());
            }
            lettersToWord.get(sortedWord).add(word);

        }
    }

    public String sortString(String word){

        char[] str = word.toCharArray();
        Arrays.sort(str);
        String result = new String(str);
        return result;

    }

    public boolean isGoodWord(String word, String base) {

        if (wordSet.contains(word)) {
            if(word.toLowerCase().contains(base.toLowerCase())){
                return false;
            }
            else
                return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabets = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for(int i=0;i<26;i++) {
            String wordwithletter = sortString(word + alphabets[i]);
            if(lettersToWord.containsKey(wordwithletter)){
                ArrayList<String> temp = lettersToWord.get(wordwithletter);
                for(int j=0;j<temp.size();j++){
                    if(temp.get(j).contains(word)){
                        temp.remove(j);
                    }
                }
                result.addAll(lettersToWord.get(wordwithletter));
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        String randomWord = "";

        int anagramCount = 0;
        boolean flag = true;
        while(flag){
            int randomNum = random.nextInt(9999);
            randomWord = wordList.get(randomNum).toString();

            String sortedWord = sortString(randomWord);
            anagramCount = lettersToWord.get(sortedWord).size();

            if(anagramCount < MIN_NUM_ANAGRAMS)
                continue;

            if(sizeTowords.get(wordLength).contains(randomWord)){
                flag = false;
                if(wordLength!= MAX_WORD_LENGTH)
                    wordLength++;
            }


        }

        return randomWord;
    }
}
