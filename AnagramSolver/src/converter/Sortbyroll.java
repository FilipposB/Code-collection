package converter;

import java.util.Comparator;

class Sortbyroll implements Comparator<Word> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(Word a, Word b) 
    { 
        return (int) (a.code - b.code); 
    } 
} 
  