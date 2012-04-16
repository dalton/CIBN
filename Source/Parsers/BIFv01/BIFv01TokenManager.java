/* Generated By:JavaCC: Do not edit this line. BIFv01TokenManager.java */
/* This parser uses the data structures in the JavaBayes core *
 * engine (package BayesianNetworks); other implementations   *
 * may use different data structures                          */
package Parsers.BIFv01;
import InterchangeFormat.*;
import java.util.Vector;
import java.util.Enumeration;

public class BIFv01TokenManager implements BIFv01Constants
{
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0xf300L) != 0L)
         {
            jjmatchedKind = 17;
            return 11;
         }
         if ((active0 & 0xc00L) != 0L)
         {
            jjmatchedKind = 17;
            return 8;
         }
         return -1;
      case 1:
         if ((active0 & 0xf300L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 1;
            return 11;
         }
         if ((active0 & 0xc00L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 1;
            return 7;
         }
         return -1;
      case 2:
         if ((active0 & 0xc00L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 2;
            return 6;
         }
         if ((active0 & 0xf300L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 2;
            return 11;
         }
         return -1;
      case 3:
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 3;
            return 5;
         }
         if ((active0 & 0x1000L) != 0L)
            return 11;
         if ((active0 & 0xe700L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 3;
            return 11;
         }
         return -1;
      case 4:
         if ((active0 & 0x6700L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 4;
            return 11;
         }
         if ((active0 & 0x8000L) != 0L)
            return 11;
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 4;
            return 4;
         }
         return -1;
      case 5:
         if ((active0 & 0x6700L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 5;
            return 11;
         }
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 5;
            return 3;
         }
         return -1;
      case 6:
         if ((active0 & 0x4100L) != 0L)
            return 11;
         if ((active0 & 0x2600L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 6;
            return 11;
         }
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 6;
            return 0;
         }
         return -1;
      case 7:
         if ((active0 & 0x400L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 7;
            return 11;
         }
         if ((active0 & 0x2200L) != 0L)
            return 11;
         if ((active0 & 0x800L) != 0L)
            return 42;
         return -1;
      case 8:
         if ((active0 & 0x400L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 8;
            return 11;
         }
         return -1;
      case 9:
         if ((active0 & 0x400L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 9;
            return 11;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 27);
      case 41:
         return jjStopAtPos(0, 28);
      case 59:
         return jjStopAtPos(0, 26);
      case 91:
         return jjStopAtPos(0, 24);
      case 93:
         return jjStopAtPos(0, 25);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x6000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0xc00L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x9000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x200L);
      case 123:
         return jjStopAtPos(0, 22);
      case 124:
         return jjStopAtPos(0, 29);
      case 125:
         return jjStopAtPos(0, 23);
      default :
         return jjMoveNfa_0(9, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x8200L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x4100L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0xc00L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0xc00L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x200L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 116:
         return jjMoveStringLiteralDfa3_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
      case 98:
         return jjMoveStringLiteralDfa4_0(active0, 0x400L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      case 101:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(3, 12, 11);
         break;
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x200L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000L);
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x800L);
      case 119:
         return jjMoveStringLiteralDfa4_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x600L);
      case 101:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(4, 15, 11);
         return jjMoveStringLiteralDfa5_0(active0, 0x800L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x100L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa6_0(active0, 0x600L);
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000L);
      case 108:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x900L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa7_0(active0, 0x400L);
      case 107:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(6, 8, 11);
         break;
      case 108:
         return jjMoveStringLiteralDfa7_0(active0, 0x200L);
      case 116:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(6, 14, 11);
         return jjMoveStringLiteralDfa7_0(active0, 0x2800L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x200L) != 0L)
            return jjStartNfaWithStates_0(7, 9, 11);
         else if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(7, 13, 11);
         break;
      case 108:
         return jjMoveStringLiteralDfa8_0(active0, 0x400L);
      case 121:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(7, 11, 42);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa9_0(active0, 0x400L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa10_0(active0, 0x400L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 121:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(10, 10, 11);
         break;
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 42;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 5:
               case 11:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 8:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 6:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 0:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAddStates(0, 3);
                  }
                  else if (curChar == 47)
                     jjAddStates(4, 5);
                  else if (curChar == 46)
                     jjCheckNAdd(15);
                  else if (curChar == 45)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(13);
                  }
                  break;
               case 42:
                  if ((0xf7ffffffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  else if (curChar == 59)
                  {
                     if (kind > 16)
                        kind = 16;
                  }
                  if ((0x3ff200000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  break;
               case 3:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 4:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 7:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 1:
                  if ((0xf7ffffffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if (curChar == 59 && kind > 16)
                     kind = 16;
                  break;
               case 10:
                  if (curChar != 45)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 12:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(13);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(13);
                  break;
               case 14:
                  if (curChar == 46)
                     jjCheckNAdd(15);
                  break;
               case 15:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddTwoStates(15, 16);
                  break;
               case 17:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(18);
                  break;
               case 18:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(18);
                  break;
               case 19:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddStates(0, 3);
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(20, 21);
                  break;
               case 21:
                  if (curChar != 46)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddTwoStates(22, 23);
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddTwoStates(22, 23);
                  break;
               case 24:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(25);
                  break;
               case 25:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(25);
                  break;
               case 26:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAddTwoStates(26, 27);
                  break;
               case 28:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(29);
                  break;
               case 29:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(29);
                  break;
               case 30:
                  if (curChar == 47)
                     jjAddStates(4, 5);
                  break;
               case 31:
                  if (curChar == 47)
                     jjCheckNAddStates(6, 8);
                  break;
               case 32:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(6, 8);
                  break;
               case 33:
               case 34:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 35:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 36:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(37, 38);
                  break;
               case 37:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(37, 38);
                  break;
               case 38:
                  if (curChar == 42)
                     jjAddStates(9, 10);
                  break;
               case 39:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(40, 38);
                  break;
               case 40:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(40, 38);
                  break;
               case 41:
                  if (curChar == 47 && kind > 6)
                     kind = 6;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 5:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 8:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 6:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 0:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 121)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 9:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 42:
                  jjCheckNAddTwoStates(1, 2);
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  break;
               case 3:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 4:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 7:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(11);
                  }
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 1:
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 10:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 11:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(11);
                  break;
               case 16:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(11, 12);
                  break;
               case 23:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(13, 14);
                  break;
               case 27:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(15, 16);
                  break;
               case 32:
                  jjAddStates(6, 8);
                  break;
               case 37:
                  jjCheckNAddTwoStates(37, 38);
                  break;
               case 39:
               case 40:
                  jjCheckNAddTwoStates(40, 38);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 42:
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 32:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(6, 8);
                  break;
               case 37:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(37, 38);
                  break;
               case 39:
               case 40:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(40, 38);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 42 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   20, 21, 26, 27, 31, 36, 32, 33, 35, 39, 41, 17, 18, 24, 25, 28, 
   29, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, "\156\145\164\167\157\162\153", 
"\166\141\162\151\141\142\154\145", "\160\162\157\142\141\142\151\154\151\164\171", 
"\160\162\157\160\145\162\164\171", "\164\171\160\145", "\144\151\163\143\162\145\164\145", 
"\144\145\146\141\165\154\164", "\164\141\142\154\145", null, null, null, null, null, null, "\173", "\175", 
"\133", "\135", "\73", "\50", "\51", "\174", };
public static final String[] lexStateNames = {
   "DEFAULT", 
};
static final long[] jjtoToken = {
   0x3fd3ff01L, 
};
static final long[] jjtoSkip = {
   0xfeL, 
};
private ASCII_CharStream input_stream;
private final int[] jjrounds = new int[42];
private final int[] jjstateSet = new int[84];
protected char curChar;
public BIFv01TokenManager(ASCII_CharStream stream)
{
   if (ASCII_CharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public BIFv01TokenManager(ASCII_CharStream stream, int lexState)
{
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(ASCII_CharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 42; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(ASCII_CharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

private final Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public final Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { 
      while (curChar <= 44 && (0x100100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

}
