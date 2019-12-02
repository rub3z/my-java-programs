///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package arraylist;
//
///**
// *
// * @author Rub3z
// */
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//public class ArrayListTest {
//
//   public static void main(String[] args) throws ParseException {
//      ArrayList list = new ArrayList();
//      SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
//      System.out.println("Add some parks:");
//      list.addFirst(new NationalPark("Pinnacles", 107.7, 196635,
//       dates.parse("2013-01-10"), "California", 36.48f, -121.16f));
//      list.addFirst(new NationalPark("Grant Teton", 1254.5, 2791392,
//       dates.parse("1929-02-26"), "Wyoming", 43.73f, -110.8f));
//      list.addFirst(new NationalPark("Grand Canyon", 4926.7, 4756771,
//       dates.parse("1919-02-26"), "Arizona", 36.06f, -112.14f));
//      list.addFirst(new NationalPark("Great Smoky Mountains", 2110.4, 10099276,
//       dates.parse("1934-06-15"), "Tennessee", 35.68f, -85.53f));
//      list.addFirst(new NationalPark("Virgin Islands", 59.4, 426930,
//       dates.parse("1956-08-02"), "United States Virgin Islands", 43.73f,
//       -110.8f));
//
//
//      list.addFirst(new NationalPark("Yellowstone", 8983.2, 3513484,
//       dates.parse("1872-03-01"), "Wyoming", 44.60f, -110.5f));
//      list.addFirst(new NationalPark("Denali", 19185.8, 531315,
//       dates.parse("1917-02-26"), "Alaska", 63.33f, -150.5f));
//      list.addFirst(new NationalPark("Zion", 593.3, 3189696,
//       dates.parse("1919-11-19"), "Utah", 37.3f, -113.05f));
//
//      System.out.println("List is now: ");
//      list.printAll();
//
//      System.out.println();
//      System.out.println("Remove item @ index 1.");
//      System.out.println("Removing " + list.removeAt(1));
//      System.out.println("List is now: ");
//      list.printAll();
//
//      System.out.println();
//      System.out.println("Insert Paul McCartney at index 2.");
//      list.insert(2, new NationalPark("Acadia", 191.9, 2563129,
//       dates.parse("1919-02-26"), "Maine", 44.35f, -68.21f));
//      System.out.println("List is now: ");
//      list.printAll();
//
//      System.out.println();
//      System.out.println("Remove the first item.");
//      System.out.println("Removing " + list.removeFirst());
//      list.printAll();
//
//      System.out.println();
//      System.out.println("Insert at index 4 a park with the same name and " +
//       "location as the first park, but in ALL CAPS:");
//      list.insert(
//       4,
//       new NationalPark(
//        list.get(0).getName().toUpperCase(),
//        list.get(0).getArea(),
//        list.get(0).getVisitors(),
//        list.get(0).getCreationDate(),
//        list.get(0).getLocation().toUpperCase(),
//        list.get(0).getLatitude(),
//        list.get(0).getLongitude()));
//
//      System.out.println("List is now: ");
//      list.printAll();
//
//      System.out.println("Clear the list:");
//      list.clear();
//      list.printAll();
//
//   }
//}
