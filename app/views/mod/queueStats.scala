package views.mod

import play.api.libs.json.Json

import lila.app.templating.Environment.{ *, given }

import lila.mod.ModActivity.Period
import lila.mod.ModQueueStats.*

object queueStats:

  def apply(p: Result)(using PageContext) =
    views.base.layout(
      title = "Queues stats",
      moreCss = cssTag("mod.activity"),
      pageModule = PageModule("mod.activity", Json.obj("op" -> "queues", "data" -> p.json)).some
    ):
      main(cls := "page-menu")(
        views.mod.ui.menu("queues"),
        div(cls := "page-menu__content index box mod-queues")(
          boxTop(
            h1(
              " Queues this ",
              periodSelector(p)
            )
          ),
          div(cls := "chart-grid")
        )
      )

  private def periodSelector(p: Result) =
    lila.ui.bits.mselect(
      s"mod-activity__period-select box__top__actions",
      span(p.period.key),
      Period.values.toList.map: per =>
        a(
          cls  := (p.period == per).option("current"),
          href := routes.Mod.queues(per.key)
        )(per.toString)
    )
