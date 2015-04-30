package dds.demo.shapes


import dds._
import dds.prelude._
import dds.config.DefaultEntities._
import rx.observables._
import rx.lang.scala._
import org.omg.dds.demo.ShapeType
import scala.concurrent.duration._
object MidPointTriangle {

  val circle = "Circle"
  val square = "Square"
  val triangle = "Triangle"

  def main(args: Array[String]) {

    def show(t: String, s: ShapeType) = {
      val x = s.x
      val y = s.y
      s"($t, $x, $y)"
    }
    val circles = DdsObservable.fromReaderData {
      DataReader[ShapeType](Topic[ShapeType](circle))
    }

    val squares = DdsObservable.fromReaderData {
      DataReader[ShapeType](Topic[ShapeType](square))
    }

    val ttopic = Topic[ShapeType](triangle)
    val tdw = DataWriter[ShapeType](ttopic)


    // Compute the average between circle and square of matching color with flatMap
    val triangles = circles.flatMap(c => squares.dropWhile(_.color != c.color).take(1).map {
      s => {
        println(show("c", c) +  " - " + show("s", s))
        new ShapeType(s.color, (s.x + c.x)/2, (s.y + c.y)/2, (s.shapesize + c.shapesize)/4)
      }
    })
    triangles.subscribe(tdw.write(_))
    // The same can be achieved with for-comprehension as shown below:

//    val cm = for {
//      c <- circles;
//      s <- squares.dropWhile(_.color != c.color).take(1)
//    } yield new ShapeType(s.color, (s.x + c.x)/2, (s.y + c.y)/2, (s.shapesize + c.shapesize)/4)
//
//
//    cm.subscribe(tdw.write(_))
  }
}
