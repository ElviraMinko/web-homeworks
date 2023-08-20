<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <#if uri_current??>
            <#if uri_current?ends_with("/index") || uri_current == "/post">
                <@sidebar/>
            </#if>
        </#if>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "USERS"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <div class="textborder">
                            <a href="${item.href}">
                                <#if uri_current?? && uri_current?ends_with(item.href)>
                                    <div class="border">${item.name}</div>
                                <#else>
                                    ${item.name}
                                </#if>

                            </a>
                        </div>
                    </li>
                </#list>
            </ul>
        </nav>

    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <@content post true/>
        </#list>
    </aside>
</#macro>

<#macro shortOrLong p isShort>
    <div class="body">
        <#if p.text?length <= 350 || !isShort>
            <p>${p.text}</p>
        <#else>
            <p>${p.text?substring(0,350)}...</p>
        </#if>
    </div>
</#macro>

<#macro content p isShort>
    <section>
        <div class="header">
            Post #${p.id}
        </div>
        <@shortOrLong p isShort/>
        <#if isShort>
            <div class="footer">
                <a href="/post?post_id=${p.id}">View all</a>
            </div>
        </#if>
    </section>
</#macro>

<#macro main_content post isShort>
    <div class="middle">
        <article>
            <div class="title">${post.title}</div>
            <div class="information">By ${c.findBy(users, "id", post.userId!).name}, 2 days ago,
                translation
            </div>
            <@shortOrLong post isShort/>
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
        </article>

    </div>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>


<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNext items key value i>
    <#if items[i][key] != value>
        <#return findNext(items, key, value, i+1)/>
    <#else>
        <#return i/>
    </#if>
</#function>

<#function countPosts items key value>
    <#assign count = 0/>
    <#list items as item>
        <#if item[key] == value>
            <#assign count=count+1/>
        </#if>
    </#list>
    <#return count/>
</#function>


