package programmingTeam.sources;/*
 * Code authored by Ruben Baerga-Ortega, 5/25/2019 to provide a better
 * solution for the Zwift coding interview question.
 */

import java.util.*;

public class BirthAndDeathTreeSolution {
   static class Person {
      int birthYear;
      int deathYear;

      public Person(int b, int d) {
         this.birthYear = b;
         this.deathYear = d;
      }
   }

   public static void main(String[] args) {
      List<Person> people = new ArrayList<>();
      people.add(new Person(1913, 1965));
      people.add(new Person(1923, 1976));
      people.add(new Person(1930, 1930));
      people.add(new Person(1891, 1933));
      people.add(new Person(1923, 2010));
      people.add(new Person(1945, 1945));
      people.add(new Person(1930, 1930));
      people.add(new Person(1970, 2010));
      people.add(new Person(-20, 35));

      /* Create a TreeMap with double value keys and String values.
       * The purpose here is to take advantage of the fact that a TreeMap
       * has its keys in sorted order and allows for duplicate values.
       * So, we can have our birth/death years sorted for us upon insertion,
       * and the values will be a String that's either "birth" or "death".
       */
      TreeMap<Double, String> years = new TreeMap<>();


      /* ONE PROBLEM: The TreeMap allows for duplicate values, but not
       * duplicate *keys*. We only have int values for years, so if more
       * than one birth or death occurs in a year then our program won't work.
       *
       * So I've made a workaround: The key values are double values;
       * and when I find a duplicate, I change the value slightly to put in
       * another birth or death accordingly for that year.
       *
       * The .put() method for TreeMap returns the previous value for that
       * key or null if there isn't one. So if it is null, we continue. If
       * it isn't, we try putting the birth or death in for a different key.
       */

      double extraBirth = 0.000;
      double extraDeath = 0.999;
      String birthOrDeath;
      for (Person p : people) {
         while (true) {
            birthOrDeath = years.put(p.birthYear + extraBirth, "birth");
            if (birthOrDeath == null ) {
               break;
            }
            extraBirth += 0.001;
         }
         while (true) {
            birthOrDeath = years.put(p.deathYear + extraDeath, "death");
            if (birthOrDeath == null) {
               break;
            }
            extraDeath -= 0.001;
         }
         extraBirth = 0.000;
         extraDeath = 0.999;
      }


      /* This is where the magic happens.
       * We use an iterator to go through the set of key-value pairs, which
       * are now conveniently sorted by year in the TreeMap.
       *
       * Each time there is a birth we increment the numberOfPeopleAlive by
       * one and decrement it for each death; updating the highest number of
       * people alive in a year along the way.
       *
       * The way I have inserted births and deaths for each year ensures that
       * births for a given year appear first; so we can see the maximum number
       * of people alive in that year before decrementing deaths. You can see
       * this happening in the output.
       */
      int numberOfPeopleAlive = 0;
      int maxNumPeople = 0;
      int greatestYear = 0;

      Set<Map.Entry<Double, String>> yearSet = years.entrySet();
      Iterator<Map.Entry<Double,String>> i = yearSet.iterator();

      while (i.hasNext()) {
         Map.Entry<Double,String> e = i.next();
         System.out.println(e.getKey() + " " + e.getValue());
         if(e.getValue().equals("birth")) {
            numberOfPeopleAlive++;
         }
         if(e.getValue().equals("death")) {
            numberOfPeopleAlive--;
         }

         if (numberOfPeopleAlive > maxNumPeople) {
            maxNumPeople = numberOfPeopleAlive;
            greatestYear = e.getKey().intValue();
         }
      }

      System.out.println("\nYear with most people alive: " + greatestYear);
      /* ...and now we print it out.
       *
       * This solution to the problem has a marked improvement in runtime
       * complexity over the array implementation. Adding to the TreeMap
       * is an O(log (n)) operation. Since this operation is performed each
       * time we have a birth and death, our runtime complexity can be said
       * to be O(p log(p)), where p is the number of people in our list.
       *
       * After perusing documentation, I found the TreeMap class uses a
       * self-balancing red-black tree implementation; so we don't really
       * need to worry about running up against a worst-case time complexity
       * of O(n^2) that would result from an unbalanced tree.
       *
       * Thanks for reading!
       *
       * Ruben B.
       */
   }
}