/**
  * Created by tzirker on 7/8/16.
  */

package LongestPath

class Node(argUniqueId: Int, argDisplayId: Int, argNeighbors: List[Int]) {

   val uniqueId = argUniqueId
   val displayId = argDisplayId
   val neighbors = argNeighbors
   var longestPath = List[Vector[Int]]()

   override def toString = ("unique:" + argUniqueId + " display:" + displayId + " neighbors:" + neighbors.toString() + " LongestPathFromHere: " + longestPath.map(_.mkString(", ")).mkString(":"))
}
