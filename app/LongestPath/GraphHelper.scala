/**
  * Created by tzirker on 7/8/16.
  */
package LongestPath

object GraphHelper {

   def makeGraphFromGrid(grid: Array[Array[Int]]): Array[Node] = {
      val gridHeight = grid.length
      val gridWidth = grid(0).length
      val graph = for (j <- 0 until gridHeight; i <- 0 until gridWidth)
         yield new Node(gridPointToGraphIndex(j, i, gridWidth, grid), grid(j)(i), getNeighborsOfGridPoint(i, j, gridHeight, gridWidth, grid))
      graph.toArray
   }


   def getNeighborsOfGridPoint(i: Int, j: Int, gridHeight: Int, gridWidth: Int, grid: Array[Array[Int]]): List[Int] = {
      ///   Looks at all the squares around a point in the grid and returns a list of valid neighbor nodes
      val neigbors = for (y <- math.max(0, j - 1) to math.min(gridHeight - 1, j + 1);
                          x <- math.max(0, i - 1) to math.min(gridWidth - 1, i + 1) if (grid(j)(i) <= grid(y)(x) && !(i == x && y == j))) yield gridPointToGraphIndex(y, x, gridWidth, grid) //grid(y)(x)-1
      neigbors.toList
   }


   def gridPointToGraphIndex(gridY: Int, gridX: Int, gridWidth: Int, grid: Array[Array[Int]]): Int = {
      gridY * gridWidth + gridX
   }

   ////   gets a comma separated string of integers and creates a grid from it
   def makeGridFromCommaSeperatedLine(commaSeparatedLine: String, gridWidth: Int): Array[Array[Int]] = {

      val arr = commaSeparatedLine.split(',').map(_.toInt)
      val grid = for (y <- Array.range(0, arr.length / gridWidth))
         yield for (x <- Array.range(0, gridWidth)) yield (arr(y * gridWidth + x))
      grid
   }

   def getLengthOfTheLongestPath(graph: Array[Node]): Int = {
      graph.map(aNode => aNode.longestPath.head.length).reduceLeft(_ max _)
   }

   //  This is used to know which gridDivs to add the css Class 'longestClass' to
   def getIdsOfAllTheNodesOnALongestPath(graph: Array[Node], longestPathLength: Int): Vector[Int] = {
      graph.filter(aNode => aNode.longestPath.head.length == longestPathLength)
         .map(aNode => aNode.longestPath.flatten.toVector).reduceLeft(_ ++ _)
   }

   ////   This makes the HTML that puts the paths onto the grid
   def makeHTMLFromGraph(graph: Array[Node], gridWidth: Int): String = {

      val idsOnALongestPath = getIdsOfAllTheNodesOnALongestPath(graph, getLengthOfTheLongestPath(graph))
      val maxDisplayId =  graph.map(aNode => aNode.displayId).reduceLeft(_ max _)

      def getGridNewlineHTML(): String = "<div style='clear:both;'></div>"
      def getGridInputHTML(aNode: Node): String = {
         val classIndexes = aNode.longestPath.reduceLeft((aPath: Vector[Int], acc: Vector[Int]) => acc ++ aPath)
         val longestClass = if (idsOnALongestPath.indexOf(aNode.uniqueId) > (-1)) " longestClass " else " "

         s"<input type='text' value='${aNode.displayId}'  class='gridCell $longestClass'>"
      }



      def recurseMakeTheSolvedGridInHtml(HTML: Vector[String], i: Int): String = {
         if (i == graph.length) {
            HTML.mkString(" ")
         } else if (i % gridWidth == 0) {   /// add a line break into the html
            recurseMakeTheSolvedGridInHtml(HTML :+ getGridNewlineHTML() + getGridInputHTML(graph(i)), i + 1)
         } else {   /// no line break, just append another gridCell div
            recurseMakeTheSolvedGridInHtml(HTML :+ getGridInputHTML(graph(i)), i + 1)
         }
      }
      recurseMakeTheSolvedGridInHtml(Vector[String](), 0)
   }


}


