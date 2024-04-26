package views.forum

import lila.app.templating.Environment.{ *, given }

import lila.forum.ForumPost

object bits:

  def searchForm(search: String = "")(using PageContext) =
    div(cls := "box__top__actions")(
      form(cls := "search", action := routes.ForumPost.search())(
        input(
          name         := "text",
          value        := search,
          placeholder  := trans.search.search.txt(),
          enterkeyhint := "search"
        )
      )
    )

  def authorLink(post: ForumPost, cssClass: Option[String] = None, withOnline: Boolean = true)(using
      PageContext
  ): Frag =
    if !isGranted(_.ModerateForum) && post.erased
    then span(cls := "author")("<erased>")
    else
      userIdLink(
        post.userId,
        cssClass = cssClass,
        withOnline = withOnline,
        modIcon = ~post.modIcon
      )

  private[forum] val dataTopic = attr("data-topic")
  private[forum] val dataUnsub = attr("data-unsub")
