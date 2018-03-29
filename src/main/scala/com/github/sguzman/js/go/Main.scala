package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.document

object Main {
  def render(items: List[AnimeMeta]) = View._render(Vars(items: _*), Var(25), Var(0), Var(""))

  def main(args: Array[String]): Unit = {
    val items = decode[Map[String, AnimeMeta]](JS.JSON.stringify(JS.Globals.shows)).right.get
    val values = items.values.toList.sortBy(_.animeHash.animeEps.anime.title)

    com.thoughtworks.binding.dom.render(document.body, render(values))
  }
}
