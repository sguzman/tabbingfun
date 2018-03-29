package com.github.sguzman.js.go

import com.definitelyscala.fullpagejs.{FullPageJsOptions, JQuery}
import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.{Binding, dom}
import io.circe.generic.auto._
import io.circe.parser.decode
import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.ClientRect
import org.scalajs.dom.{Element, document, window}

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSGlobalScope}

object Main {
  @dom def _render(items: Vars[AnimeMeta]): com.thoughtworks.binding.Binding[org.scalajs.dom.html.Div] = {
    <div>
      <header>
        <h1 id="header">Anime</h1>
      </header>
      <main>
        <div id="container">
          <ul>
            {
              for (i <- items) yield {
                <li class="anime-list-item">
                  <div class="div-item">
                    <img class="thumbnail" title={i.animeHash.animeEps.anime.img} />
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

  def render(items: Vars[AnimeMeta]): Binding[Div] = _render(items)

  @js.native
  @JSGlobalScope
  object Globals extends js.Object {
    val shows: js.Dictionary[js.Any] = js.native
  }

  @js.native
  @JSGlobal("JSON")
  object JSON extends js.Object {
    def parse(text: String): js.Any = js.native

    def stringify(value: js.Any): String = js.native
  }

  final case class Anime(animeUrl: String, title: String, img: String, id: Int, start: Int, end: String)
  final case class AnimeEps(anime: Anime, eps: List[String])
  final case class AnimeHash(animeEps: AnimeEps, vids: List[String])
  final case class AnimeMeta(animeHash: AnimeHash, links: List[String])

  def _inViewPort(rect: ClientRect): Boolean = {
    overlap(rect.top, rect.bottom, 0, window.innerHeight)
  }
  def inViewPort(e: Element): Boolean = _inViewPort(e.getBoundingClientRect)

  def overlap(x1: Double, x2: Double, y1: Double, y2: Double): Boolean =
    if (x1 > y1) overlap(y1, y2, x1, x2)
    else x2 >= y1

  def main(args: Array[String]): Unit = {
    val items = decode[Map[String, AnimeMeta]](JSON.stringify(Globals.shows)).right.get
    val values = Vars[AnimeMeta](items.values.toSeq.sortBy(_.animeHash.animeEps.anime.title): _*)
    com.thoughtworks.binding.dom.render(document.body, render(values))

    val tmp = document.getElementsByClassName("thumbnail")

    val imgs = mutable.ListBuffer((for (i <- 0 until tmp.length) yield tmp.item(i).asInstanceOf[Element]): _*)
    imgs.foreach{a =>
      if (inViewPort(a)) {
        a.setAttribute("src", a.getAttribute("title"))
        imgs -= a
      }
    }

    (new JQuery{}).fullpage(new FullPageJsOptions {})

    window.onscroll = (e) => {
      imgs.foreach{a =>
        if (inViewPort(a.parentNode.asInstanceOf[Element])) {
          a.setAttribute("src", a.getAttribute("title"))
          imgs -= a
        }
      }
    }
  }
}
