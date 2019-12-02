package psychic;////package psychic;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// *
// * @author Rub3z
// */
//public class Psychic {
//
//   /**
//    * @param args the command line arguments
//    */
//   public static void main(String[] args) throws IOException {
//
//      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//      String line;
//
//      while ((line = reader.readLine()) != null) {
//         String input[] = line.split(" ");
//
//         int[] values = new int[input.length];
//
//         for (int i = 0; i < input.length; i++) {
//            if (Character.isDigit(input[i].charAt(0)))
//               values[i] = Character.digit(input[i].charAt(0), 10);
//            else {
//               switch (input[i].charAt(0)) {
//                  case 'A':
//                     values[i] = 1;
//                     break;
//                  case 'T':
//                     values[i] = 10;
//                     break;
//                  case 'J':
//                     values[i] = 11;
//                     break;
//                  case 'Q':
//                     values[i] = 12;
//                     break;
//                  case 'K':
//                     values[i] = 13;
//                     break;
//                  default:
//                     break;
//               }
//            }
//
//            switch (input[i].charAt(1)) {
//               case 'C':
//                  values[i] *= 1;
//                  break;
//               case 'D':
//                  values[i] *= 2;
//                  break;
//               case 'H':
//                  values[i] *= 3;
//                  break;
//               case 'S':
//                  values[i] *= 4;
//                  break;
//               default:
//                  break;
//            }
//
//
//
//            int temp = 0;
//            for(int j = 0; j < values.length; j++)
//            {
//              for(int s = 1; s < (values.length - j); s++)
//              {
//                if( values[s-1] > values[s])
//                {
//                  temp = values[s-1];
//                  values[s-1] = values[s];
//                  values[s] = temp;
//                }
//              }
//            }
//
//
//
//            int[] card = new int[10];
//            int[] suit = new int[10];
//
//            for(int j = 0; j < values.length; j++){
//               card[j] = values[j] % 13;
//               suit[j] = values[j] / 13;
//            }
//
//            int isStraight = checkStraight(card);
//        int isFlush = checkFlush(suit);
//
//        if(card[4] == 12 && isStraight == 1 && isFlush == 1)
//        {
//           royal++;
//           //System.out.println("Royal Flush");
//        }
//        else if(isStraight == 1 && isFlush == 1)
//        {
//          sflush++;
//          //System.out.println("Straight Flush");
//        }
//        else if(card[0] == card[1] && card[1] == card[2] && card[2] == card[3] || card[1] == card[2] && card[2] == card[3] && card[3] == card[4])
//        {
//          quads++;
//          //System.out.println("Quad");
//        }
//        else if(card[0] == card[1] && card[2] == card[3] && card[3] == card[4] || card[0] == card[1] && card[1] == card[2] && card[3] == card[4])
//        {
//          fullh++;
//          //System.out.println("Full House");
//        }
//        else if(isFlush == 1)
//        {
//          flush++;
//          //System.out.println("Flush");
//        }
//        else if(isStraight == 1)
//        {
//          straight++;
//          //System.out.println("Straight");
//        }
//        else if(card[0] == card[1] && card[1] == card[2] || card[1] == card[2] && card[2] == card[3] || card[2] == card[3] && card[3] == card[4])
//        {
//          triple++;
//          //System.out.println("Triple");
//        }
//        else if(card[0] == card[1] && card[2] == card[3] || card[0] == card[1] && card[3] == card[4] || card[2] == card[3] && card[3] == card[4])
//        {
//          twoPair++;
//          //System.out.println("Two Pair");
//        }
//        else if(card[0] == card[1] || card[1] == card[2] || card[2] == card[3] || card[3] == card[4])
//        {
//          onePair++;
//          //System.out.println("Pair");
//        }
//        else
//        {
//          highCard++;
//          //System.out.println("High Card");
//        }
//
//        //Pull the next hand of five cards from the deck
//        nextHand = nextHand + 5;
//      }
//    again++;
//    }
//
//
//
//
//
//         }
//
//
//
//
//      }
//
//   }
//
//
//
//
//
//}
