package dds.demo.shapes

import dds.config.DefaultEntities._
import dds._
import rx.observables._
import org.omg.dds.demo.ShapeType

object SquareRace {
  val size = 30
  val y0 = 30
  val delta = 3
  val ycoord = Map[String, Int](
    "RED"     -> y0,
    "GREEN"   -> 2*y0,
    "BLUE"    -> 3*y0,
    "ORANGE"  -> 4*y0,
    "YELLOW"  -> 5*y0,
    "MAGENTA" -> 6*y0,
    "CYAN"    -> 7*y0,
    "GRAY"    -> 8*y0
  )

  val xcoord = scala.collection.mutable.Map[String, Int](
    "RED"     -> 0,
    "GREEN"   -> 0,
    "BLUE"    -> 0,
    "ORANGE"  -> 0,
    "YELLOW"  -> 0,
    "MAGENTA" -> 0,
    "CYAN"    -> 0,
    "GRAY"    -> 0
  )
  def main(args: Array[String]): Unit = {

    val circles = DdsObservable.fromReaderData {
      DataReader[ShapeType](Topic[ShapeType]("Circle"))
    }

    val sdw = DataWriter[ShapeType](Topic[ShapeType]("Square"))

    val colors = circles.filter(s => {
      s.x == 0 || s.y == 0
    }).map(s => {
      val x = xcoord(s.color) + delta 
      xcoord(s.color) = x
      new ShapeType(s.color, x, ycoord(s.color), size)
    })

    colors.subscribe(sdw.write(_))
  }
}
