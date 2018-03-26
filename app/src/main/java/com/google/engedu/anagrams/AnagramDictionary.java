/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int defaultword;
    private Random random = new Random();
    ArrayList <String>WordList;
    HashSet <String>wordset;
    HashMap <String,ArrayList<String>> letterstoWord;
    HashMap <Integer,ArrayList<String>> sizetoword;
    public AnagramDictionary(Reader reader) throws IOException {

        BufferedReader in = new BufferedReader(reader);
        defaultword=DEFAULT_WORD_LENGTH;
        WordList=new ArrayList<String>();
        wordset=new HashSet<String>();
        sizetoword=new HashMap<Integer, ArrayList<String>>();
        letterstoWord=new HashMap<String, ArrayList<String>>();
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            WordList.add(word);
            wordset.add(word);

        }
        for (String i:wordset
             )
        {
            String k=sortletters(i);
            if(letterstoWord.containsKey(k))
            {
                letterstoWord.get(k).add(i);
            }
            else
            {
                ArrayList <String> al =new ArrayList<String>();
                al.add(i);
                letterstoWord.put(k,al);

            }
            if(sizetoword.containsKey(i.length()))
            {
                sizetoword.get(i.length()).add(i);
            }
            else
            {
                ArrayList <String> al =new ArrayList<String>();
                al.add(i);
                sizetoword.put(i.length(),al);

            }


        }

        }

    public String sortletters(String word)
    {
        char[] characters=word.toCharArray();
        Arrays.sort(characters);
        String s=new String(characters);
        return s;


    }


    public boolean isGoodWord(String word, String base) {
        for (String i : wordset) {
            if (word.equals(i) && !word.contains(base)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String str=sortletters(targetWord);
       if(letterstoWord.containsKey(str))
       {
           result=letterstoWord.get(str);
       }


        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String newword;
        result.addAll(getAnagrams(word));
        char c='a';
        for(int i=0;i<=25;i++)
        {
            c+=i;
            newword=sortletters(word + c);
            if(letterstoWord.get(newword)!=null)
            {
                for (String j:letterstoWord.get(newword))
                     {
                    result.add(j);
                }
            }
        }
        result.remove(word);
        return result;
    }

    public String pickGoodStarterWord() {
        defaultword++;
        if(defaultword>MAX_WORD_LENGTH)
            defaultword=DEFAULT_WORD_LENGTH;
        ArrayList<String> r = new ArrayList<String>();
        r.addAll(sizetoword.get(defaultword));
        String wrd = r.get(random.nextInt(r.size()));
        while (getAnagramsWithOneMoreLetter(wrd).size()<MIN_NUM_ANAGRAMS) {
           wrd=r.get(random.nextInt(r.size()));

        }
        return wrd;
    }

}
