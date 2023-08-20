<#-- @ftlvariable name="loginOrEmail" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="article form-box">
        <div class="header">Create article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" wrap = "hard" rows="15" cols="23" ></textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $('#text').val();
                const $error = $(this).find(".error");
                ajaxWithoutLists({
                    action: "create",
                    title: title,
                    text: text
                }, $error)
                return false;
            });
        })
    </script>

</@c.page>












<#--</div>-->
<#--</div>-->
<#--<div class="error"></div>-->
<#--<div class="button-field">-->
<#--    <input type="submit" value="Create">-->
<#--</div>-->
<#--</form>-->
<#--</div>-->
<#--</div>-->

<#--<script>-->
<#--    $(function () {-->
<#--        $(".article form").submit(function () {-->
<#--            const title = $(this).find("input[name='title']").val();-->
<#--            const text = $(this).find("input[name='text']").val();-->
<#--            const $error = $(this).find(".error");-->
<#--            ajax({-->
<#--                action: "action",-->
<#--                title: title,-->
<#--                text: text-->
<#--            }, $error)-->
<#--            return false;-->
<#--        });-->
<#--    })-->
<#--</script>-->

<#--</main>-->
<#--</div>-->
<#--<footer>-->
<#--    <a href="/index">Codeforces</a> © 2022 by Mike Mirzayanov-->
<#--</footer>-->
<#--<script>-->
<#--    $(function () {-->
<#--    })-->
<#--</script>-->
<#--</body>-->
<#--</html>-->
