package com.github.sguzman.js.go

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSGlobalScope}

object JS {
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
}
