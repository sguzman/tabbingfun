package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.html.{Div, Select}
import org.scalajs.dom.{Event, document}

object Main {
  @dom def _render(items: List[AnimeMeta], pageSize: Var[Int], idx: Var[Int]): Binding[Div] = {
    <div>
      <header>
        <h1 id="header">Anime</h1>
      </header>
      <main>
        <div id="container">
          <button onclick={_: Event => idx.value -= 1} disabled={idx.bind == 0}>Prev</button>
          <button onclick={_: Event => idx.value += 1} disabled={idx.bind == items.length - 1}>Next</button>
          <select onchange={e: Event => println(e.asInstanceOf[Select].value)}>
            <option value="10">10</option>
            <option value="25" selected={true}>25</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </select>
          <ul>
            {
              for (i <- items.grouped(pageSize.bind).map(a => Vars[AnimeMeta](a: _*)).toList(idx.bind)) yield {
                <li class="anime-list-item">
                  <div class="div-item">
                    <img class="thumbnail" src={i.animeHash.animeEps.anime.img} />
                    <p class="mini-title">{i.animeHash.animeEps.anime.title}</p>
                    <aside>{i.links.length.toString} Episode(s)</aside>
                  </div>
                </li>
              }
            }
          </ul>
        </div>
      </main>
    </div>
  }

  def render(items: List[AnimeMeta]): Binding[Div] = _render(items, Var(25), Var(1))

  def main(args: Array[String]): Unit = {
    val items = decode[Map[String, AnimeMeta]](JS.JSON.stringify(JS.Globals.shows)).right.get
    val values = items.values.toList.sortBy(_.animeHash.animeEps.anime.title)
    com.thoughtworks.binding.dom.render(document.body, render(values))
  }
}
