package com.company;

import java.util.Scanner;

class HashTable{

    private static String[] hashTable;
    private static int total = 0;

    public HashTable(String[] input){
        hashTable = input;
    }

    public static boolean check(int slot, String check){
        if(hashTable[slot].equals(check)){
            return true;
        }
        else{
            total++;
            return false;
        }
    }

    public static int getTotal(){
        return total;
    }
}

public class Main {

    public static void main(String[] args) {

        String str1 = new String("hello");
        String str2 = new String("hello");
        String[] stringArray = new String[2];
        stringArray[0] = str1;
        stringArray[1] = str2;

        String[] hash1 = fill(100, stringArray);
        HashTable hash2 = new HashTable(hash1);

        int result = find(100, hash2, "hello");

        System.out.println(HashTable.getTotal());
    }

    public static int find(int size, HashTable myTable, String word) {
        long counter = 1;
        int constantA = 129;
        long uniqueNumber = 0;
        int modUniqueNum = 0;
        int jumpSize = 0;
        boolean spaceFree = false;

        //step 1: calculate the unique number.
        for (int j = 0; j < word.length(); j++) {
            uniqueNumber += word.charAt(j) * counter;
            counter = counter * constantA;
        }
        //step2: modulus the unique number.
        modUniqueNum = (int) (uniqueNumber % size);

        //step3: call check method on the myTable
        boolean result = myTable.check(modUniqueNum,word);

        //step4: if result is false then go to double hash jumps
        if(!result){
            //step 5: calculate the jump size.
            int max = 7;
            jumpSize = (int) (max - (uniqueNumber % max));

            //step 6: keep adding jumpsize until you find an empty slot.
            do {
                modUniqueNum = modUniqueNum + jumpSize;
                //step 7: if modUniqueNum overruns the hash table loop it back around.
                if (modUniqueNum >= size) {
                    modUniqueNum = modUniqueNum - size;
                }
                //step 8: call the check method again on the updated location.
                result = myTable.check(modUniqueNum,word);

                //step 8: close loop when free space is found.
            } while (!result);
        }
        return 0;
    }

    public static String[] fill(int size, String[] array) {
        long counter = 1;
        int constantA = 129;
        long uniqueNumber = 0;
        int modUniqueNum = 0;
        int jumpSize = 0;
        boolean spaceFree = false;

        //step 0: create hash table
        String[] hash1 = new String[size];

        for (int i = 0; i < array.length; i++) {
            String temp = array[i];

            //step 1: calculate the unique number.
            for (int j = 0; j < temp.length(); j++) {
                uniqueNumber += temp.charAt(j) * counter;
                counter = counter * constantA;
            }
            //step2: modulus the unique number.
            modUniqueNum = (int) (uniqueNumber % size);
            //step3: check if place is free/ if so insert value into hash table.
            if (hash1[modUniqueNum] == null) {
                hash1[modUniqueNum] = array[i];
            }
            //step4: if place is not free / implement Double Hashing.
            else {
                //step 5: calculate the jump size.
                int max = 7;
                jumpSize = (int) (max - (uniqueNumber % max));

                //step 6: keep adding jumpsize until you find an empty slot.
                do {
                    modUniqueNum = modUniqueNum + jumpSize;
                    //step 7: if modUniqueNum overruns the hash table loop it back around.
                    if (modUniqueNum >= size) {
                        modUniqueNum = modUniqueNum - size;
                    }
                    //step 8: when you find an empty space insert the word.
                    if (hash1[modUniqueNum] != null) {
                        hash1[modUniqueNum] = array[i];
                        spaceFree = true;
                    }
                    //step 9: close loop when free space is found.
                } while (!spaceFree);
            }

            //step 10: reset counters for next word.
            uniqueNumber = 0;
            modUniqueNum = 0;
            counter = 1;
            jumpSize = 0;
            spaceFree = false;
        }
        return hash1;
    }
}