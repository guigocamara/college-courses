/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Guilherme Camara
| Language: Java
|
| To Compile: javac pa02.java
|
| To Execute: java -> java pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2021
| Instructor: McAlpin
| Due Date: 11/21/2021
|
+=============================================================================*/

import java.io.*;
import java.util.*;

public class pa02 
{
    public static void main(String[] args)
    {
        // Incorrect value message
        if((!(args[1] == "8") && (args[1] == "16") && (args[1] == "32")))
        {
            System.err.print("\nValid checksum sizes are 8, 16, or 32\n");
            System.exit(1);
        }

      
        int numberOfBits = Integer.parseInt(args[1]);
   
        String newString = adjustedString(numberOfBits, args[0]);

        int checksum = checkSum(numberOfBits, newString);

        int checkSumSize = numberOfBits;

        int characterCnt = newString.length();

        System.out.print("\n");
        System.out.printf("%2d bit checksum is %8s for all %4d chars\n", checkSumSize, Integer.toHexString(checksum), characterCnt); 
        // decimal value is converted to hexadecimal value
    }


    public static int checkSum(int numberOfBits, String originalString)
    {
        switch(numberOfBits)
        {
            case 8:
                return bit8(originalString);
            case 16:
                return bit16(originalString);
            case 32:
                return bit32(originalString);
            default:
                return 1;
        }
    }

    public static int bit8(String originalString)
    {
        int checkValue = 0;
        
        for(int i = 0; i < originalString.length(); i++)
        {
            checkValue += originalString.charAt(i);
        }

        return checkValue & 0xFF; // bit masking
    }

    public static int bit16(String originalString)
    {
        int checkValue = 0;

        for(int i = 0; i < originalString.length(); i += 2)
        {
            checkValue += originalString.charAt(i) << 8;
            checkValue += originalString.charAt(i + 1);
        }

        return checkValue & 0xFFFF; // bit masking


    }

    public static int bit32(String originalString)
    {
        int checkValue = 0;

        for(int i = 0; i < originalString.length(); i += 4)
        {
            checkValue += originalString.charAt(i) << 24;
            checkValue += originalString.charAt(i + 1) << 16;
            checkValue += originalString.charAt(i + 2) << 8;
            checkValue += originalString.charAt(i + 3);
        }

        return checkValue & 0xFFFFFFFF; // bit masking
    }

        public static String adjustedString(int numberOfBits, String originalString)
    {
          // Every input file has a single line of text terminated by the hexadecimal // character ’0A’ or the NEWLINE character.
          try
          {
          File file = new File(originalString);
          Scanner scanner = new Scanner(file);
          scanner.useDelimiter("\\Z");
          String newString = scanner.next() + "\n";


          while(newString.length() % (numberOfBits/8) != 0)
                newString = newString + "X";

          for(int i = 0; i < newString.length(); i++)
          {
              if(i % 80 == 0)
              System.out.print("\n");
              System.out.print(newString.charAt(i));
          }

          scanner.close();
          return newString;
          }
          catch(FileNotFoundException e)
          {
              System.exit(1);
          }

          return "1";
    }

	
}

/*=============================================================================
| I, Guilherme Camara, gu618739, affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/