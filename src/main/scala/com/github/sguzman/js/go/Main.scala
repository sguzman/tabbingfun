package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Div, Select}

object Main {
  implicit final class StrWrap(str: String) {
    def id[A] = document.getElementById(str).asInstanceOf[A]
  }

  @dom def render(idx: Var[Int]): Binding[Div] = {
    <div>
      <select id="select" onchange={_: Event =>
        idx.value = "select".id[Select].selectedIndex
        (1 to 5).map(_.toString).foreach(a => a.id[Div].style.display = if (a == ("select".id[Select].selectedIndex + 1).toString) "block" else "none" )
      }>
        <option selected={true} value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
      </select>
      <div class="tabs" id="1">
        <h1>1</h1>
      </div>
      <div class="tabs" id="2">
        <h1>2</h1>
      </div>
      <div class="tabs" id="3">
        <h1>3</h1>
      </div>
      <div class="tabs" id="4">
        <h1>4</h1>
      </div>
      <div class="tabs" id="5">
        <h1>5</h1>
      </div>
    </div>
  }


  def main(args: Array[String]): Unit = {
    com.thoughtworks.binding.dom.render(document.body, render(Var(1)))
    "1".id[Div].style.display = "block"
  }
}
