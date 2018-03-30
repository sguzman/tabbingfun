package com.github.sguzman.js.go

import com.thoughtworks.binding.Binding.{Var, Vars}
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Div, Select, Span}
import org.scalajs.dom.document

object View {
  implicit final class StrWrap(str: String) {
    def id[A] = document.getElementById(str).asInstanceOf[A]
  }

  @dom def buttons(idx: Var[Int], pageSize: Var[Int], length: Int): Binding[Span] = {
    <span>
      <button disabled={idx.bind == 0} onclick={_: Event => idx.value -= 1}>Prev</button>
      <button disabled={Math.ceil(length / pageSize.bind) - 1 == idx.bind} onclick={_: Event => idx.value += 1}>Next</button>
    </span>
  }

  @dom def select(idx: Var[Int], pageSize: Var[Int]): Binding[Select] = {
    <select id="select" onchange={e: Event =>
      pageSize.value = "select".id[Select].value.toInt
      idx.value = 0
    }>
      <option value="10">10</option>
      <option value="25" selected={true}>25</option>
      <option value="50">50</option>
      <option value="100">100</option>
      <option value="100">1000</option>
    </select>
  }

  @dom def modal(anime: Var[Model], ep: Var[Int]): Binding[Div] = {
    <div id="modal">

      <!-- Modal content -->
      <div class="modal-content">
        <span onclick={_: Event => "modal".id[Div].style.display = "none"} id="close">&times;</span>
        <div>
          <h2>{anime.bind.title}</h2>
          <h3>{anime.bind.eps.length.toString} Episode(s)</h3>
          <img src={s"http:${anime.bind.img}"} />
          <div>
            <p>Episode {ep.bind.toString}</p>
            <button onclick={_: Event => ep.value -= 1} disabled={ep.bind == 0}>Prev</button>
            <button onclick={_: Event => ep.value += 1} disabled={anime.bind.eps.length - 1 == ep.bind}>Next</button>
            <iframe src={s"https:${anime.bind.eps(ep.bind)}"}></iframe>
          </div>
        </div>
        <p>{anime.bind.desc}</p>
      </div>

    </div>
  }

  @dom def _render(items: List[Model], pageSize: Var[Int], idx: Var[Int], anime: Var[Model], ep: Var[Int]): Binding[Div] = {
    <div>
      <header>
        <h1>Anime</h1>
      </header>
      <article>
        {modal(anime, ep).bind}
        <div id="container">
          {select(idx, pageSize).bind}
          {buttons(idx, pageSize, items.length).bind}
          <ul>
            {
              for (i <- Vars(items.grouped(pageSize.bind).toList(idx.bind): _*)) yield {
                <li class="anime-list-item">
                  <div onclick={_: Event =>
                    anime.value = i
                    "modal".id[Div].style.display = "block"
                  } class="div-item">
                    <b class="mini-title">{i.title}</b>
                    <img class="thumbnail" src={s"http:${i.img}"} />
                    <aside>{i.eps.length.toString} episode(s)</aside>
                  </div>
                </li>
              }
            }
          </ul>
        </div>
      </article>
    </div>
  }
}
