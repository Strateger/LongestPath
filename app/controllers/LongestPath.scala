package controllers

import javax.inject._
import javax.swing.text.html.HTML

import LongestPath.GraphHelper
import play.api._
import play.api.mvc._
import play.api.routing._
import play.api.routing.sird._


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class LongestPath @Inject() extends Controller {

   /**
     * This is accessed by going to `/theLongestPathInTheGraph`.
     */
   def longestPath = Action {

      Ok(views.html.longestpath())
   }


   def findLongestPath(gridWidth: Int) = Action { request =>

      val commaValues = request.body.asText.getOrElse("None");
      val grid = LongestPath.GraphHelper.makeGridFromCommaSeperatedLine(commaValues, gridWidth)
      val graph = GraphHelper.makeGraphFromGrid(grid)
      val pathFinder = new LongestPath.LongestPathFinder(graph)
      pathFinder.findLongestPathInGraph() /// the results go into pathFinder.graph
      val HTML = LongestPath.GraphHelper.makeHTMLFromGraph(pathFinder.graph, gridWidth)
      Ok(HTML + "<script> setGridClassSize(); animateGridCellColorChange(); colorLongestPath(); </script>")
   }


   def javascriptRoutes = Action { implicit request =>
      Ok(
         JavaScriptReverseRouter("jsRoutes")(
            routes.javascript.LongestPath.findLongestPath
         )
      ).as("text/javascript")
   }

}
