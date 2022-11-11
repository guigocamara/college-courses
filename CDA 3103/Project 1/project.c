// Authors: Shivam Shah and Guilherme Camara
// Instructor: Sarah Angell
// CDA 3103
// Fall 2021

#include "spimcore.h"
#include <stdio.h>

/* ALU */
/* 10 Points */
void ALU(unsigned A,unsigned B,char ALUControl,unsigned *ALUresult,char *Zero)
{
    switch(ALUControl)
    {
      //  Z = A + B
      case '0': *ALUresult = A + B; 
              break;

      // Z = A - B
      case '1': *ALUresult = A - B;
              break;

      // if A < B, Z = 1; otherwise, Z = 0
      case '2': 
              if((signed)A < (signed)B) *ALUresult = 1;
              else *ALUresult = 0;
              break;

      // If A < B, Z = 1; otherwise, Z = 0 (A and B are unsigned integers)
      case '3':
              if(A < B) *ALUresult = 1;
              else *ALUresult = 0;
              break;

      // Z = A AND B
      case '4':
              *ALUresult = A & B;
              break;

      // Z = A OR B
      case '5':
              *ALUresult = A | B;
              break;

      // Z = Shift B left by 16 bits
      case '6':
              *ALUresult = B << 16;
              break;

      // Z = NOT A
      case '7':
              *ALUresult = !A; 
              break;

      //default : break;
    }

    if(*ALUresult == 0)
      *Zero = '1';
    else
      *Zero = '0';
}

/* instruction fetch */
/* 10 Points */
/*
1. Fetch the instruction addressed by PC from Mem and write it to instruction.

2. Return 1 if a halt condition occurs; otherwise, return 0. 
*/
int instruction_fetch(unsigned PC,unsigned *Mem,unsigned *instruction)
{
    // Mem has already been populated
    // PC is current index used for instruction Memory

    // Check for word alignment, return 1 in case of 
    // halt condition 
    if((PC % 4 != 0))
    {
      return 1;
    }
    
    // Mem is an array of words in C, so we have to
    // shift bits to get the actual location
    else
    {
      *instruction = Mem[PC >> 2];
    
      return 0;
    }
    
}


/* instruction partition */
/* 10 Points */
/*
1. Partition instruction into several parts (op, r1, r2, r3, funct, offset, jsec).

2. Read line 41 to 47 of spimcore.c for more information.
*/
void instruction_partition(unsigned instruction, unsigned *op, unsigned *r1,unsigned *r2, unsigned *r3, unsigned *funct, unsigned *offset, unsigned *jsec)
{
    *op = (instruction) >> 26; // [31-26]
    *r1 = (instruction >> 21) & 0x0000001F;
    *r2 = (instruction >> 16) & 0x0000001F;
    *r3 = (instruction >>11) & 0x0000001F; // [15-11]
    *funct = (instruction & 0x0000003F); // [5-0]
    *offset = (instruction & 0x0000FFFF); // [15-0]
    *jsec = (instruction & 0x3FFFFFF); // [25-0]
    
}



/* instruction decode */
/* 15 Points */
/*
1. Decode the instruction using the opcode (op).

2. Assign the values of the control signals to the variables in the structure controls
(See spimcore.h file).
The meanings of the values of the control signals:
For MemRead, MemWrite or RegWrite, the value 1 means that enabled, 0
means that disabled, 2 means “don’t care”.
For RegDst, Jump, Branch, MemtoReg or ALUSrc, the value 0 or 1 indicates the
selected path of the multiplexer; 2 means “don’t care”.
The following table shows the meaning of the values of ALUOp.

3. Return 1 if a halt condition occurs; otherwise, return 0.
*/
int instruction_decode(unsigned op,struct_controls *controls)
{
    switch(op)
    {
      case 0:
        	 controls->RegDst = '1';
           controls->Jump = '0';
           controls->Branch = '0';
           controls->MemRead = '0';
           controls->MemtoReg = '0';
           controls->ALUOp = '7';
           controls->MemWrite = '0';
           controls->ALUSrc = '0';
           controls->RegWrite = '1';
           
           break;

      case 2:
            controls->RegDst = '2';
            controls->Jump = '1';
            controls->Branch = '0';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '0';
            controls->ALUOp = '0';
            
            break;

      case 4:
            controls->RegDst = '2';
            controls->Jump = '0';
            controls->Branch = '1';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->ALUOp = '1';
            controls->MemWrite = '0';
            controls->ALUSrc = '0';
            controls->RegWrite = '0';
            
            break;

      case 8:
            controls->RegDst = '0';
            controls->Jump = '0';
            controls->Branch = '0';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->ALUOp = '0';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '1';
          
            break;
      
      case 10:
            controls->RegDst = '0';
            controls->Jump = '0';
            controls->Branch = '0';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '1';
            controls->ALUOp = '2';
          
            break;

      case 11:
            controls->RegDst = '0';
            controls->Jump = '0';
            controls->Branch = '2';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->ALUOp = '3';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '1';
            
            break;

      case 15:
            controls->RegDst = '0';
            controls->Jump = '0';
            controls->Branch = '0';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '1';
            controls->ALUOp = '6';
            
            break;

      case 35:
            controls->RegDst = '0';
            controls->Jump = '0';
            controls->Branch = '0';
            controls->MemRead = '1';
            controls->MemtoReg = '1';
            controls->ALUOp = '0';
            controls->MemWrite = '0';
            controls->ALUSrc = '1';
            controls->RegWrite = '1';
            
            break;

      case 43:
            controls->RegDst = '2';
            controls->Jump = '0';
            controls->Branch = '0';
            controls->MemRead = '0';
            controls->MemtoReg = '0';
            controls->ALUOp = '0';
            controls->MemWrite = '1';
            controls->ALUSrc = '1';
            controls->RegWrite = '0';
 
            break;
      default:
            return 1; 
    }
    return 0;
}

/* Read Register */
/* Read the registers addressed by r1 and r2 from Reg, and write the read values
to data1 and data2 respectively. */
/* 5 Points */
void read_register(unsigned r1,unsigned r2,unsigned *Reg,unsigned *data1,unsigned *data2)
{
    // Reg is an array containing the register file
    *data1 = Reg[r1];
    *data2 = Reg[r2];
}


/* Sign Extend */
/* 10 Points */
/*
  1. Assign the sign-extended value of offset to extended_value.
*/
void sign_extend(unsigned offset,unsigned *extended_value)
{
    // 16th bit is the sign bit
    unsigned signBit = offset >> 15;

    if(signBit == 1) // if number is negative
      *extended_value = offset | 0b11111111111111110000000000000000;
    else
      *extended_value = offset | 0b00000000000000000000000000000000;
}

/* ALU operations */
/* 10 Points */
/*
  1. Apply ALU operations on data1, and data2 or extended_value (determined by
  ALUSrc).

  2. The operation performed is based on ALUOp and funct.

  3. Apply the function ALU(…).

  4. Output the result to ALUresult.

  5. Return 1 if a halt condition occurs; otherwise, return 0.
*/

int ALU_operations(unsigned data1,unsigned data2,unsigned extended_value,unsigned funct,char ALUOp,char ALUSrc,unsigned *ALUresult,char *Zero)
{
	if (ALUSrc == '0')
	{
		if(ALUOp== '1')
    {
      ALU(data1, data2, '1', ALUresult, Zero);
			return 0;
    }
    else
    {
      switch(funct)
      {
        
        case 32:
                ALU(data1, data2, '0', ALUresult, Zero); 
			          return 0;
        case 34:
                ALU(data1, data2, '1', ALUresult, Zero); 
			          return 0;
        case 36:
                ALU(data1, data2, '4', ALUresult, Zero); 
			          return 0;
        case 37:
                ALU(data1, data2, '5', ALUresult, Zero); 
			          return 0;
        case 42:
                ALU(data1, data2, '2', ALUresult, Zero); 
			          return 0;
        case 43:
                ALU(data1, data2, '3', ALUresult, Zero); 
			          return 0;
        default : return 1;
      }
    }
	}
	else if (ALUSrc == '1') 
	{
		switch(ALUOp)
      {
        
        case '0':
                ALU(data1, extended_value, ALUOp, ALUresult, Zero); 
			          return 0;
        case '2':
                ALU(data1, extended_value, ALUOp, ALUresult, Zero); 
			          return 0;
        case '3':
                ALU(data1, extended_value, ALUOp, ALUresult, Zero); 
			          return 0;
        case '6':
                ALU(data1, extended_value, ALUOp, ALUresult, Zero); 
			          return 0;
        default : return 1;
      }
	}
	else
	{
		return 1;
	}
}

// New function created to check for halt conditions
int checkHolt(unsigned ALUresult, char MemRead, char MemWrite)
{
  
  if(MemWrite == '1' || MemRead == '1')
  {
    if((ALUresult % 4) != 0) return 1; //word alligned
    if(ALUresult > 65536) return 1; //out of bounds
  }
  
    return 0;
}



/* Read / Write Memory */
/* 10 Points */
/*
  1. Use the value of MemWrite or MemRead to determine if a memory write
  operation or memory read operation or neither is occurring.

  2. If reading from memory, read the content of the memory location addressed by

  3. If writing to memory, write the value of data2 to the memory location
  addressed by ALUresult.

  4. Return 1 if a halt condition occurs; otherwise, return 0.
*/


int rw_memory(unsigned ALUresult,unsigned data2,char MemWrite,char MemRead,unsigned *memdata,unsigned *Mem)
{
    // Mem is the memory array
    // Possible halt conditions: ALUresult is out of bounds or is not word aligned
    
    int h;
    h = checkHolt(ALUresult,MemRead, MemWrite);
    if(h == 1) return 1;

    
    // shift bits to get the actual location
    ALUresult = ALUresult >> 2;

    if(MemRead == '1') // If MemRead = 1, read from memory
    {
        *memdata = Mem[ALUresult];
    }
    if(MemWrite == '1') //  If MemWrite = 1, write into memory
    {
        Mem[ALUresult] = data2; 
    }

     return 0;
}


/* Write Register */
/* 10 Points */
// 1. Write the data (ALUresult or memdata) to a register (Reg) addressed by r2 or r3.
void write_register(unsigned r2,unsigned r3,unsigned memdata,unsigned ALUresult,char RegWrite,char RegDst,char MemtoReg,unsigned *Reg)
{
  if(RegWrite == '1') // If RegWrite == 1, place write data into the register specified by RegDst
  {
        if(MemtoReg == '1') // If RegWrite == 1, and MemtoReg == 1, then data is coming from memory
        {
            if(RegDst == '1')
            {
              Reg[r3] = memdata;
            }
            else if(RegDst == '0')
            {
              Reg[r2] = memdata;
            }
        }
        else if(MemtoReg == '0') // If RegWrite == 1, and MemtoReg == 0, then data is coming from ALU_result
        {
          if(RegDst == '1')
            {
              Reg[r3] = ALUresult;
            }
            else if(RegDst == '0')
            {
              Reg[r2] = ALUresult;
            }
        }
  }   
}

/* PC update */
/* 10 Points */
// 1. Update the program counter (PC).
      //- PC = PC + 4;
      //- Jump: Left shift bits of jsec by 2 and use upper 4 bits of PC
      //- Take care of Branch and Jump
      //- Zero – Branch taken or not

void PC_update(unsigned jsec,unsigned extended_value,char Branch,char Jump,char Zero,unsigned *PC)
{
	*PC += 4;
  
  if (Jump == '1')
	{
		*PC = (*PC & 0xF0000000) + (jsec << 2);	
	}
	else if (Branch == '1' && Zero == '1')
	{
		*PC = *PC + (extended_value << 2); 
	}
}
