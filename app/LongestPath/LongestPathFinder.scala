/**
  * Created by t on 7/10/16.
  */

package LongestPath


class LongestPathFinder(argGraph: Array[Node]) {
   val graph = argGraph

/*
Finds the very longest paths in the graph.   If there are 3 paths of the same length that are the longest, then it will return all 3 paths
 */
   def findLongestPathInGraph(): List[Vector[Int]] = {
      val allPaths = graph.sortWith(_.displayId > _.displayId).map(node => (getLongestPathStartingAtNode(node.uniqueId)))
      allPaths.reduceLeft((aPath: List[Vector[Int]], acc: List[Vector[Int]]) => if (aPath.head.length > acc.head.length) aPath else acc)

   }


   /*
   def getLongestPathStartingAtNode(graphIndex : Int) : List[Vector[Int]]
      Finds the longest path starting at a specified index in the graph

      If this.graph was made with a grid like this   --->  grid = Array( Array(1,2),  Array(3,4),  Array(6,7))
      Calling this function would yield:   List(Vector(5, 4, 3))
    */
   def getLongestPathStartingAtNode(graphIndex: Int): List[Vector[Int]] = {
      def recursor(pathsSoFar: List[Vector[Int]]): List[Vector[Int]] = {
         val nextSetOfPaths = pathsSoFar.flatMap(aPath => {
            graph(aPath.head).neighbors.diff(aPath).map { neighborsOfHeadOfEachPath => neighborsOfHeadOfEachPath +: aPath }
         })

         if (nextSetOfPaths.length == 0) {
            pathsSoFar
         }
         else {
            recursor(nextSetOfPaths)
         }
      }

      graph(graphIndex).longestPath = recursor(List(Vector(graphIndex)))
      graph(graphIndex).longestPath
   }


}
