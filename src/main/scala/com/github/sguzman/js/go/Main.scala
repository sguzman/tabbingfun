package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.Var
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.document

object Main {
  def render(items: List[AnimeMeta]) = View._render(items, Var(25), Var(1))

  def main(args: Array[String]): Unit = {
    val items = decode[Map[String, AnimeMeta]](JS.JSON.stringify(JS.Globals.shows)).right.get
    val values = items.values.toList.sortBy(_.animeHash.animeEps.anime.title)
    com.thoughtworks.binding.dom.render(document.body, render(values))
  }
}
