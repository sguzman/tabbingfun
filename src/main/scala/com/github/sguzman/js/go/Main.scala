package com.github.sguzman.js.go

import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.document
import org.scalajs.dom.html.Div

object Main {
  @dom def _render: com.thoughtworks.binding.Binding[org.scalajs.dom.html.Div] = {
    <div>
      <header>
        <h1>HELLO</h1>
      </header>
      <main>
      </main>
    </div>
  }

  def render: Binding[Div] = _render

  def main(args: Array[String]): Unit = {
    com.thoughtworks.binding.dom.render(document.body, render)
  }
}
