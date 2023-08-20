<#import "./common.ftlh" as c>
<#-- @ftlvariable name="id" type="java.lang.Long" -->
<@c.page>
    <#list posts?reverse as post>
        <#if post.userId==user_id>
           <@c.main_content post true/>
            <div class="footer">
                <a href="/post?post_id=${post.id}">View all</a>
            </div>
        </#if>
    </#list>
</@c.page>