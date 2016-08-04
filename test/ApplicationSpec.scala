import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import LongestPath._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {
    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }
  }

  "HomeController" should {
    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include (" :)")
    }
  }


  var grid1 = Array(
    Array(1,2),
    Array(3,4),
    Array(5,5)
  )

  val graph =  GraphHelper.makeGraphFromGrid(grid1)


  "Testing grid1 " should {
    " graph(0).neighbors should be List(1,2,3)" in {
      assert( graph(0).neighbors === List(1,2,3))
    }
  }

  "Testing grid1 " should {
    " Neighbors of graph(0) should be List(1,2,3)" in {
      assert(graph(0).neighbors === List(1, 2, 3))
    }
  }
  "Testing grid1 " should {
    " Neighbors of graph(4) should be List(5)" in {
      assert( graph(4).neighbors === List(5))
    }
  }

  "Testing grid1 " should {
    " Neighbors of graph(5)" in {
      assert( graph(5).neighbors === List(4))
    }
  }



  var grid2 = Array(
    Array(2,1,2),
    Array(4,10,3),
    Array(4,9,4)
  )

  "A Solved split longest path test from grid2 " should {
    "be List(Vector(4, 7, 6, 3, 0, 1), Vector(4, 7, 8, 5, 2, 1)))" in {
      assert(  (new LongestPathFinder( GraphHelper.makeGraphFromGrid(grid2)))
         .getLongestPathStartingAtNode(1) ===  List(Vector(4, 7, 6, 3, 0, 1), Vector(4, 7, 8, 5, 2, 1)))
    }
  }

  val grid3 = LongestPath.GraphHelper.makeGridFromCommaSeperatedLine("2,1,2,4,10,3,4,9,4", 3)
  "Make a grid from a comma separated line of Integers " should {
    "be  Array(Array(2,1,2),Array(4,10,3),Array(4,9,4))" in {
      assert( grid3 ===  Array(
                            Array(2,1,2),
                            Array(4,10,3),
                            Array(4,9,4)
                          ))
    }
  }

}
