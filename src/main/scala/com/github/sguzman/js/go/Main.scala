package com.github.sguzman.js.go

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.html.Div

object Main {
  @dom def render: Binding[Div] = {
    <div>
      <h1>Test</h1>
    </div>
  }


  def main(args: Array[String]): Unit = {
    com.thoughtworks.binding.dom.render(document.body, render)
  }
}
