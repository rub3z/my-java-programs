package graphicviolence;
/**
 * CECS 328 MW 1PM
 * Lab 6: Graphic Violence
 * Ruben Baerga ID #010366978
 * 
 * @author Rub3z using a template by Neal Terrell
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WeightedGraph {

   class WeightedNode {
      int mIndex;
      private List<WeightedEdgy> mNeighbors = new ArrayList<>();
      
      WeightedNode(int index) {
         mIndex = index;
      }
   }

   private class WeightedEdgy implements Comparable {

      private WeightedNode mFirst, mSecond;
      private double mWeight;

      WeightedEdgy(WeightedNode first, WeightedNode second, double weight) {
         mFirst = first;
         mSecond = second;
         mWeight = weight;
      }

      @Override
      public int compareTo(Object o) {
         WeightedEdgy e = (WeightedEdgy) o;
         return Double.compare(mWeight, e.mWeight);
      }
   }

   private List<WeightedNode> mVertices = new ArrayList<>();
   
   public WeightedGraph(int numberOfVertices) {
      for (int i = 0; i < numberOfVertices; i++) {
         mVertices.add(new WeightedNode(i));
      }
   }

   public void addEdgy(int firstVertex, int secondVertex, double weight) {
      WeightedNode first = mVertices.get(firstVertex);
      WeightedNode second = mVertices.get(secondVertex);
      
      WeightedEdgy edgy = new WeightedEdgy(first, second, weight);
      first.mNeighbors.add(edgy);
      second.mNeighbors.add(edgy);
   }

   /**
    * Prints the graph to the console. Each vertex is printed on its own line,
    * starting with the vertex's number (its index in the mVertices list), then
    * a colon, then a sequence of pairs for each edge incident to the vertex.
    * For each edge, print the number of the vertex at the opposite end of the
    * edge, as well as the edge's weight.
    *
    * Example: in a graph with three vertices (0, 1, and 2), with edges from 0
    * to 1 of weight 10, and from 0 to 2 of weight 20, printGraph() should print
    *
    * Vertex 0: (1, 10), (2, 20) Vertex 1: (0, 10) Vertex 2: (0, 20)
    */
   public void printGraph() {
      for (WeightedNode n : mVertices) {
         // Print out the vertex index.
         System.out.print("Vertex " + n.mIndex + ": ");
         for (WeightedEdgy e : n.mNeighbors) {
            System.out.print("(" 
             // Compare the first vertex of edge e with n,
             // Since we have no way of knowing which vertex to print otherwise.
             + ((e.mFirst == n) ? e.mSecond.mIndex : e.mFirst.mIndex) + ", "
             + (int)(e.mWeight) + ")" 
             // Now we make sure this is the LAST edge in the n's neighbors
             // stupid freakin' last comma ruins everything
             + ((e.equals(n.mNeighbors.get(n.mNeighbors.size() - 1))) ?
                  "" : ",") + " ");
         }
         System.out.println("");
      }
      System.out.println("");
   }

   /**
    * Applies Prim's algorithm to build and return a minimum spanning tree for
    * the graph. Start by constructing a new WeightedGraph with the same number
    * of vertices as this graph. Then apply Prim's algorithm. Each time an edge
    * is selected by Prim's, add an equivalent edge to the other graph. When
    * complete, return the new graph, which is the minimum spanning tree.
    *
    * @return an UnweightedGraph representing this graph's minimum spanning
    * tree.
    */
   public WeightedGraph getMinimumSpanningTree() {
      WeightedGraph mst = new WeightedGraph(mVertices.size());
      
      PriorityQueue<WeightedEdgy> edgelords = new PriorityQueue<>();
      
      HashSet<WeightedNode> marked = new HashSet<>();
      
      int i = 0;
      marked.add(mVertices.get(0));
      mVertices.get(0).mNeighbors.stream().forEach(edgelords::add);
      
      // Instead of using double for loops or something like that, we can
      // go until we've removed every single edge from edgelords.
      // After all, the remaining edges will simply get polled and have
      // their vertices bounced off of the HashSet like rubber balls;
      // since they've already been added. Inefficient, but simpler to write.
      while (edgelords.size() > 0) {
         WeightedEdgy temp = edgelords.poll();
         if(marked.add(temp.mFirst)) {
            temp.mFirst.mNeighbors.stream().forEach(edgelords::add);
            mst.addEdgy(temp.mFirst.mIndex, temp.mSecond.mIndex, temp.mWeight);
         }
         else if(marked.add(temp.mSecond)) {
            temp.mSecond.mNeighbors.stream().forEach(edgelords::add);
            mst.addEdgy(temp.mFirst.mIndex, temp.mSecond.mIndex, temp.mWeight);
         }
      }
      
      return mst;
   }
   
   /**
    * Applies Dijkstra's algorithm to compute the shortest paths from a source
    * vertex to all other vertices in the graph. Returns an array of path
    * lengths; each value in the array gives the length of the shortest path
    * from the source vertex to the corresponding vertex in the array.
    */
   public double[] getShortestPathsFrom(int source) {
      // TODO: apply Dijkstra's algorithm and return the distances array.
      
      // This queue is used to select the vertex with the smallest "d" value
      // so far.
      // Each time a "d" value is changed by the algorithm, the corresponding
      // DijkstraDistance object needs to be removed and then re-added to
      // the queue so its position updates.
      PriorityQueue<DijkstraDistance> vertexQueue = 
       new PriorityQueue<DijkstraDistance>();
      
      // Initialization: set the distance of the source node to 0, and all
      // others to infinity. Add all distances to the vertex queue.
      DijkstraDistance[] distances = new DijkstraDistance[mVertices.size()];
      distances[source] = new DijkstraDistance(source, 0);
      for (int i = 0; i < distances.length; i++) {
         if (i != source)
            distances[i] = new DijkstraDistance(i, Integer.MAX_VALUE);
         
         vertexQueue.add(distances[i]);
      }

      while (vertexQueue.size() > 0) {
         // Finish the algorithm.
         DijkstraDistance temp = vertexQueue.poll();
         for (WeightedEdgy v : mVertices.get(temp.mVertex).mNeighbors) {
            double len = temp.mCurrentDistance + v.mWeight;
            int index = (temp.mVertex == v.mFirst.mIndex) ? 
             v.mSecond.mIndex :
             v.mFirst.mIndex;
            if (len < distances[index].mCurrentDistance) {
               distances[index].mCurrentDistance = len;
               vertexQueue.add(distances[index]);
            }
         }
      }
      
      double[] temp = new double[mVertices.size()];
      for (int i = 0; i < distances.length; i++) {
         temp[i] = distances[i].mCurrentDistance;
      }
      
      return temp;
   }
   
   class DijkstraDistance implements Comparable {
   
      int mVertex;
      double mCurrentDistance;
      
      DijkstraDistance(int vertex, double distance) {
         mVertex = vertex;
         mCurrentDistance = distance;
      }
      
      @Override
      public int compareTo(Object other) {
         DijkstraDistance d = (DijkstraDistance) other;
         return Double.compare(mCurrentDistance, d.mCurrentDistance);
      }
   }

   public static void main(String[] args) throws FileNotFoundException {
      System.out.println("Enter the name of your file, bro:");
      Scanner in = new Scanner(System.in);
      
      File graphFile = new File(in.nextLine());
      
      Scanner fin = new Scanner(graphFile);
      
      WeightedGraph wg = new WeightedGraph(Integer.parseInt(fin.nextLine()));
      
      System.out.println("Loading ur graph...");
      int i = 0;
      String s = "";
      while (fin.hasNextLine()) {
         s = fin.nextLine();
         
         if (!s.equals("")) {
            String[] edgys = s.split(" ");

            for (int j = 0; j < edgys.length; j += 2) {
               wg.addEdgy(i,                       //First vertex
                Integer.parseInt(edgys[j]),        //Second vertex
                Double.parseDouble(edgys[j+1]));   //Weight
            }
         }
         i++;
      }
      System.out.println("...done.");
      
      int input = 0;
      
      while (input != 4) {
         System.out.println("Use the number keys to select an option, man:\n"
          + "1. Print ur precious little graph.\n"
          + "2. Print the pretty and prim minimum spanning tree of ur graph\n"
//          + "  You see what I did there? Huh? Because it's made with\n"
//          + "  PRIM'S algorithm!!! HAHAHAHAHA LOL GET IT!?!?!...\n"
//          + "  I regret nothing.\n"
          + "3. Print the shortest paths from a vertex.\n"
          + "4. Exit. No more graphs for you.");
         
         input = in.nextInt();
         
         switch(input) {
            case (1):
               System.out.println("\nHere ya go:");
               wg.printGraph();
               break;
            case (2):
               WeightedGraph wgmst = wg.getMinimumSpanningTree();
               System.out.println("\nMinimum spanning tree:");
               wgmst.printGraph();
               break;
            case (3):
               System.out.println("\nEnter the index of the source vertex:");
               input = in.nextInt();
               double[] theDistances = wg.getShortestPathsFrom(input);
               for (int j = 0; j < theDistances.length; j++) 
                  System.out.println("Distance from " + input + " to " + j + 
                   ": " + theDistances[j]);
               System.out.println("");
               input = 1337;
               break;
            case (4):
               System.out.println("\nBye, loser.");
               break;
            default:
               System.out.println("\nHey! Enter a valid number, noob.\n");
               break;
         }
      }
   }
}
