<#import "./common.ftlh" as c>
<#-- @ftlvariable name="id" type="java.lang.Long" -->
<@c.page>
    <#assign post_it =  c.findBy(posts, "id", post_id!)!/>
    <#if post_it?has_content>
        <@c.main_content post_it false/>
         <#else>

        No such post
    </#if>
<@c.footer/>
</@c.page>