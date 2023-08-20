<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->


<#import "commons.ftlh" as c>
<@c.page>
    <div class="myArticles datatable">

        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <form method="post" action="">
                        <input type="hidden" name="action" value="hide"/>
                        <td class="id"></td>
                        <td class="title"></td>
                        <td>
                            <div class="button-field">
                                <input class="button-submit" type="submit" value="">
                            </div>
                        </td>
                    </form>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <script>
        $(function () {

            const $noData = $(".myArticles .noData");
            $noData.find("td").text("Loading...");
            reload = function () {
                ajax({
                        action: "findAll"
                    },
                    function (response) {
                        $(".myArticles tr").remove();
                        response["myArticles"].forEach(
                            function (article) {
                                $noData.hide();
                                var $tr = $($(".myArticles template").prop("content")).clone();
                                $tr.find(".id").text(article["id"])
                                $tr.find(".title").text(article["title"])
                                let value
                                let flag
                                if (article["hidden"]) {
                                    value = "Show";
                                } else {
                                    value = "Hide";
                                }
                                var i = article["id"]
                                var str = "complete(" + i + ", \"" + value + "\")"
                                // alert(str)
                                $tr.find(".button-submit").attr({
                                    "class": "button-submit",
                                    "type": "submit",
                                    "value": value,
                                    "onclick": str

                                });
                                $(".myArticles tbody").append($tr);
                            })


                    }
                )
            };
            complete = function (postId, value) {

                ajax({
                        action: "changeVisibility",
                        id: postId,
                        button: value
                    }, (r) => reload()
                );
                return false;
            };
            reload();

        })

    </script>
</@c.page>
