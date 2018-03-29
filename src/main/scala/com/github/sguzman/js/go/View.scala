package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Div, Input, Select}

object View {
  @dom def _render(items: Vars[AnimeMeta], pageSize: Var[Int], idx: Var[Int], text: Var[String]): Binding[Div] = {
    <div>
      <header>
        <h1 id="header">Anime</h1>
      </header>
      <main>
        <div id="container">
          <input oninput={e: Event => text.value = e.currentTarget.asInstanceOf[Input].value} placeholder="Search"></input>
          <button onclick={_: Event => idx.value -= 1} disabled={idx.bind == 0}>Prev</button>
          <button onclick={_: Event => idx.value += 1} disabled={idx.bind == Math.ceil(items.bind.length / pageSize.bind) - 1}>Next</button>
          <select onchange={e: Event =>
            pageSize.value = e.currentTarget.asInstanceOf[Select].value.toInt
            idx.value = 0
          }>
            <option value="10">10</option>
            <option value="25" selected={true}>25</option>
            <option value="50">50</option>
            <option value="100">100</option>
            <option value="100">1000</option>
          </select>
          <ul>
            {
            for (i <- items.bind.grouped(pageSize.bind).map(a => for (j <- Vars[AnimeMeta](a: _*) if text.bind.isEmpty || j.animeHash.animeEps.anime.title.contains(text.bind)) yield j).toList(idx.bind)) yield {
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
}
