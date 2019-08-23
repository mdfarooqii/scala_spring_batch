package org.springscala.configuration

import java.util

import org.springframework.batch.item.ItemWriter

class ConsoleItemWriter[T] extends ItemWriter [T]{
  override def write(list: util.List[_ <: T]): Unit = {

    import scala.collection.JavaConversions._
    for (item <- list) {
      System.out.println(item)
    }

  }
}
