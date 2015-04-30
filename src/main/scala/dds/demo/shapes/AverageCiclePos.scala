package dds.demo.shapes

import dds._
import dds.prelude._
import dds.config.DefaultEntities._
import rx.observables._
import rx.lang.scala._
import org.omg.dds.demo.ShapeType
import scala.concurrent.duration._

object AverageCiclePos {
  val circle = "Circle"
  val square = "Square"
  val triangle = "Triangle"

  def main(args: Array[String]) {
    if (args.length > 1) {
      val h = args(0).toInt
      val inc = args(1).toInt

      val circleT = Topic[ShapeType](circle)
      val cdw = DataWriter[ShapeType](circleT)
      val cobs = DdsObservable.fromReaderData {
        DataReader[ShapeType](circleT)
      }

      val ttopic = Topic[ShapeType](triangle)
      val tobs = DdsObservable.fromReaderData {
        DataReader[ShapeType](ttopic)
      }

      val tdw = DataWriter[ShapeType](ttopic)

      val gcircles: Observable[(String, Observable[ShapeType])] = cobs.groupBy(s => s.color)



      val avgCirclePos = cobs.groupBy(_.color).map(_._2).flatMap(s => s.slidingBuffer(h, inc)).map(bs => {
        val n = bs.length
        val as = bs.tail.foldRight(bs.head)((a, s) => new ShapeType(a.color, a.x + s.x, a.y + s.y, a.shapesize + s.shapesize))
        new ShapeType(as.color, as.x / n, as.y / n, as.shapesize / n)
      })

      avgCirclePos.subscribe(tdw.write(_))

    }
    else
      println("USAGE:\n\t ShapeAnalytics <history> <increment>")
  }
}
