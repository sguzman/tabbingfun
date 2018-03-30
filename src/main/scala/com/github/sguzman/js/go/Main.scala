package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.Var
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.document

object Main {
  def render(items: List[Model]) =
    View._render(items, Var(25), Var(0), Var(items.head), Var(0))

  def main(args: Array[String]): Unit = {
    val items = decode[List[Model]](JS.JSON.stringify(JS.Globals.shows)).right.get
    com.thoughtworks.binding.dom.render(document.body, render(items))
  }
}
