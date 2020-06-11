package com.tinilite.tiniview;

public class spi {

   static String spiCodeASHex;

   static short characterMap [] ={

                    0x0, 0x0,    //0
                    0xEE, 0xEE,  //1
                    0xDD, 0xDD,  //2
                    0xBB, 0xB7,  //3
                    0x77, 0x7B,  //4
                    0xF7, 0xF4,  //5
                    0xFF, 0x8B,  //6
                    0x8F, 0x7F,  //7
                    0x78, 0xFF,  //8
                    0x2A, 0xB2,  //9
                    0x3A, 0xE2,  //10  A
                    0x2F, 0xA3,  //11
                    0xEB, 0xA6,  //12
                    0xF7, 0x2B,  //13
                    0xEB, 0xA2,  //14
                    0xFB, 0xE2,  //15
                    0xFF, 0xFF,  //16
                    0xFF, 0xFF,  //17
                    0xFF, 0xFF,  //18
                    0xFF, 0xFF,  //19
                    0xFF, 0xFF,  //20
                    0xFF, 0xFF,  //21
                    0xFF, 0xFF,  //22
                    0xFF, 0xFF,  //23
                    0x63, 0x3B,  //24
                    0x7D, 0x79,  //25
                    0x63, 0x22,  //26
                    0x72, 0xFF,  //27
                    0xFD, 0xE5,  //28
                    0x67, 0x3B,  //29
                    0xD7, 0x5F,  //30
                    0xF5, 0x7D,  //31
                    0xFF, 0xFF,  //32  space
                    0xBE, 0xFF,  //33  !
                    0xF7, 0xF7,  //34  "
                    0x0, 0x0,   //35   #
                    0x23, 0x32,  //36  $
                    0x25, 0x52,  //37  %
                    0xA1, 0x1C,  //38  &
                    0xFD, 0xFF,  //39  '
                    0xDD, 0xFF,  //40  (
                    0xFF, 0xDD,  //41  )
                    0x55, 0x59,  //42  *
                    0x77, 0x7B,  //43  +
                    0xFF, 0xDF,  //44  ,
                    0x7F, 0xFB,  //45  -
                    0xFF, 0x2B,  //46  .
                    0xFD, 0xDF,  //47  /
                    0xA8, 0x86,  //48  0
                    0xBE, 0xFF,  //49  1
                    0x6A, 0xAA,  //50  2
                    0x2A, 0xBE,  //51  3
                    0x3E, 0xF3,  //52  4
                    0xCB, 0xB2,  //53  5
                    0x2B, 0xA2,  //54  6
                    0xBA, 0xFE,  //55  7
                    0x2A, 0xA2,  //56  8
                    0x2A, 0xB2,  //57  9
                    0xF7, 0x7F,  //58  :
                    0xF7, 0xDF,  //58  ;
                    0xDD, 0xFF,  //60  <
                    0x6F, 0xBB,  //61  =
                    0xFF, 0xDD,  //62  >
                    0x7A, 0x7F,  //63  ?
                    0x62, 0xA6,  //64  @
                    0x3A, 0xE2,  //65  A
                    0x22, 0x3E,  //66  B
                    0xEB, 0xA6,  //67  C
                    0xA2, 0x3E, //68  D
                    0x6B, 0xA2,  //69  E
                    0xFB, 0xE2,  //70  F
                    0x2B, 0xA6,  //71  G
                    0x3E, 0xE3,  //72  H
                    0xF7, 0x7F,  //73  I
                    0xAE, 0xAF,  //74  J
                    0xDD, 0xE3,  //75  K
                    0xEF, 0xA7,  //76  L
                    0xBC, 0xE5,  //77  M
                    0x9E, 0xE5,  //78  N
                    0xAA, 0xA6,  //79  O
                    0x7A, 0xE2,  //80  P
                    0x8A, 0xA6,  //81  Q
                    0x5A, 0xE2,  //82  R
                    0x2B, 0xB2,  //83  S
                    0xF3, 0x7E,  //84  T
                    0xAE, 0xA7,  //85  U
                    0xFD, 0xC7,  //86  V
                    0x9E, 0xC7,  //87  W
                    0xDD, 0xDD,  //88  X
                    0xFD, 0x7D,  //89  Y
                    0xE9, 0x9E,  //90  Z
                    0xE3, 0x7F,  //91  [
                    0xDF, 0xFD,  //92  \
                    0xF7, 0x3E,  //92  ]
                    0xF8, 0xDF,  //94  ^
                    0xEF, 0xBF,  //95  _
                    0xFF, 0xFD,  //96  `
                    0x3A, 0xE2,  //97  a
                    0x22, 0x3E,  //98  b
                    0xEB, 0xA6,  //99  c
                    0xA2, 0x3E,  //100  d
                    0x6B, 0xA2, //101   e
                    0xFB, 0xE2,  //102  f
                    0x2B, 0xA6,  //103  g
                    0xFF, 0xFF,  //104  h
                    0xFF, 0xFF,  //105  i
                    0xFF, 0xFF,  //106  j
                    0xFF, 0xFF,  //107  k
                    0xFF, 0xFF,  //108  l
                    0xFF, 0xFF,  //109  m
                    0xFF, 0xFF,  //110  n
                    0xFF, 0xFF  //111   o
            };

   public static String  getSpiCodeASHex(){
       return spiCodeASHex;
   }

   static byte[] generateSpiCode(String pLine){
       byte[] returnval = new byte[pLine.length() * 2 ];
       char [] lineAsChar = pLine.toCharArray();
       char thischar;
       for (int i = 0; i <pLine.length(); i++){
           thischar = lineAsChar[pLine.length() - i - 1];


           returnval[2*i] = (byte) characterMap[ 2* thischar] ;
           returnval[2*i + 1] = (byte) characterMap[ 2* thischar + 1] ;

       }
       return returnval;
   }

   static String spicodeToHex (byte[] pSpiCode){
       String returnval = "";
       for (int i = 0; i < pSpiCode.length; i ++ ){
            returnval = returnval + Integer.toHexString( pSpiCode[i]);
       }
       returnval = returnval.toUpperCase();
       return returnval;
   }

   static void displayLine(String pLine){
       byte[] spicode = generateSpiCode (pLine);

       spiCodeASHex = spicodeToHex(spicode);
       //make sure latch low

       //transmit over spi

       //drive latch hi
       //drive latch low

   }

}
