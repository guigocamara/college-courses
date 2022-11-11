#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*=============================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Guilherme Camara
| Language: c
|
| To Compile:
| gcc -o pa01 pa01.c
| 
|
| To Execute:
| c -> ./pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2021
| Instructor: McAlpin
| Due Date: 10/26/2021
|
+=============================================================================*/


int main(int argc,char* argv[])
{
  FILE *keyFP;
  char ch, key[513];
  int i = 0;

  keyFP = fopen(argv[1], "r");

  printf("\n");
  printf("\n");
  printf("Vigenere Key:\n");
  printf("\n");
  while ((ch = fgetc(keyFP)) != EOF)
  {
       if(!isalpha(ch))
          continue;
       
           if (isupper(ch))
           {
             ch = tolower(ch);
           }
       
       if(isalpha(ch))
       {
       key[i] = ch;
       i++;
       if (i > 512)
          break;
       }
  }

  key[i] = '\0';
  for(int a = 0; a < strlen(key); a++)
  {
    printf("%c", key[a]);
  }
  
  printf("\n");
  fclose(keyFP);


  FILE *ptFP;
  char plaintext[513];
  int j = 0;

  ptFP = fopen(argv[2], "r");

  printf("\n");
  printf("\n");
  printf("Plaintext:\n");

   while ((ch = fgetc(ptFP)) != EOF)
  {
       if(!isalpha(ch))
          continue;
       
          
           if (isupper(ch))
           {
             ch = tolower(ch);
           }
       
       if(isalpha(ch))
       {
       plaintext[j] = ch;
       j++;
       if (j > 512)
          break;
       }

  }

  while(j < 512)
  {
    plaintext[j] = 'x';
    j++;
  }

  plaintext[j] = '\0';


  fclose(ptFP);

  
  int msgLen = strlen(plaintext);
  int keyLen = strlen(key);

  char updatedKey[msgLen];
  char cipherText[msgLen];

  
   for(int a = 0; a < strlen(plaintext); a++)
  {
    if(a%80 == 0)
    printf("\n");
    printf("%c",tolower(plaintext[a]));
  }
  

  printf("\n");
  printf("\n\nCiphertext:\n");
  

  for(int i = 0; i < msgLen; i++)
  {
    cipherText[i] = ((plaintext[i] + key[i]) % 26) + 'A';
  }

  for(int i = 0; i < strlen(cipherText); i++)
  {
    if(i%80 == 0)
    printf("\n");
    printf("%c",tolower(cipherText[i]));
  }
  
  printf("\n");

  return 0;
}



/*=============================================================================
| I, Guilherme Camara, gu618739, affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/