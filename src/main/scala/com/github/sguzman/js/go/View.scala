package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Div, Input, Select}


object View {
  final implicit class StrWrap(str: String) {
    def id[A] = document.getElementById(str).asInstanceOf[A]
  }


  @dom def _render(items: Vars[AnimeMeta], current: Var[AnimeMeta], pageSize: Var[Int], idx: Var[Int], text: Var[String], epIdx: Var[Int]): Binding[Div] = {
    <div>
      <header>
        <h1 id="header">Anime</h1>
      </header>
      <main>
        <div id="modal" class="modal">

          <!-- Modal content -->
          <div class="modal-content">
            <span onclick={_: Event => "modal".id[Div].style.display = "none"} id="close">&times;</span>
            <div>
              <h2>{current.bind.animeHash.animeEps.anime.title}</h2>
              <h3>{current.bind.links.length.toString} Episode(s)</h3>
              <img src={current.bind.animeHash.animeEps.anime.img} />
              <div>
                <p>Episode {epIdx.bind.toString}</p>
                <div onclick={_: Event => epIdx.value -= 1} disabled={epIdx.bind == 0}>Prev</div>
                <div onclick={_: Event => epIdx.value += 1} disabled={current.bind.links.length - 1 == epIdx.bind}>Next</div>
                <video>
                  <source src={current.bind.links(epIdx.bind)}></source>
                </video>
              </div>
            </div>
            <p>Some text in the Modal..</p>
          </div>

        </div>
        <div id="container">
          <input oninput={e: Event => text.value = e.currentTarget.asInstanceOf[Input].value} placeholder="Search"></input>
          <button onclick={_: Event => idx.value -= 1} disabled={idx.bind == 0}>Prev</button>
          <button onclick={_: Event => idx.value += 1} disabled={idx.bind == Math.ceil(items.value.length / pageSize.bind) - 1}>Next</button>
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
            for (i <- items.value.grouped(pageSize.bind).map(a => for (j <- Vars[AnimeMeta](a: _*) if text.bind.isEmpty || j.animeHash.animeEps.anime.title.contains(text.bind)) yield j).toList(idx.bind)) yield {
              <li class="anime-list-item">
                <div onclick={_: Event =>
                current.value = i
                "modal".id[Div].style.display = "block"
               } class="div-item">
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
