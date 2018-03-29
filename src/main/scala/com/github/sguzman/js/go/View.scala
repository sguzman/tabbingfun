package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Div, Select}

object View {
  @dom def _render(items: List[AnimeMeta], pageSize: Var[Int], idx: Var[Int]): Binding[Div] = {
    <div>
      <header>
        <h1 id="header">Anime</h1>
      </header>
      <main>
        <div id="container">
          <button onclick={_: Event => idx.value -= 1} disabled={idx.bind == 0}>Prev</button>
          <button onclick={_: Event => idx.value += 1} disabled={idx.bind == items.length - 1}>Next</button>
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
}
