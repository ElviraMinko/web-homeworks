<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]"-->
<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!
    <div class="articles datatable">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <article>
            <template>
                <div class="title">Codeforces Round #510 (Div. 2)</div>
                <div class="user-id"></div>
                <pre class="text"></pre>
                <ul class="attachment">
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        2 days ago
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </template>
        </article>


        <div class="noData">
            <div colspan="3">
                No data
            </div>
        </div>
    </div>

    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.find("div").text("No articles");
            ajax({
                    action: "findAll"
                },

                function (response) {
                    response["articles"].forEach(function (article) {
                        $noData.hide();
                        const $div = $($(".articles template ").prop("content")).clone();
                        $div.find(".user-id").text(response["users"].find(user => user["id"] === article["userId"])["login"]);
                        $div.find(".title").text(article["title"]);
                        $div.find(".text").text(article["text"]);
                        $div.find("div").attr("data-userId", article["id"]);
                        $(".articles article").append($div);
                    });
                    $(".articles article").click(function () {
                        ajaxWithLists({
                            action: "findArticle",
                            userId: $(this).attr("data-userId")
                        }, "article")
                    });
                }
            );
        })
    </script>
</@c.page>












