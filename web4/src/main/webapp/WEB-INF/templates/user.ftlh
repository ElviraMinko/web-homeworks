<#-- @ftlvariable name="id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>

    <#assign u =  (c.findBy(users, "handle", handle!))!(c.findBy(users, "id", user_id!))!/>
    <#assign i = (c.findNext(users, "handle", handle!, 0))!(c.findNext(users, "id", user_id!, 0))!/>


    <#if u?has_content>
        <div class="enter-box">
            <div class="centre">
                <h1>
                    <a href="/user?user_id=${u.id}">${u.name}</a>
                </h1>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>number of posts: <a href="/posts?user_id=${u.id}">${c.countPosts(posts, "userId", u.id)}</a></li>
                </ul>
            </div>
            <div>
                <img src="img/task1.jpg" alt="task1"/>
            </div>
        </div>

        <div class="pointers">
            <#if (i==0)>
                <p class="str674"></p>
            <#else>
                <#if (users[i-1])?has_content>
                    <a href="http://localhost:8080/user?handle=${users[i-1].handle}"><p class="str675"></p></a>
                </#if>
            </#if>
            <#if (users[i+1])?has_content>
                <a href="http://localhost:8080/user?handle=${users[i+1].handle}"><p class="str676"></p></a>
            <#else>
                <p class="str673"></p>
            </#if>

        </div>
    <#else>
        No such user
    </#if>

</@c.page>
