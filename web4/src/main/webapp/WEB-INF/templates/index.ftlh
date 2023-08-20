<#import "./common.ftlh" as c>

<@c.page>
    <body>

        <#list posts?reverse as post>
           <@c.main_content post true/>
        </#list>

    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2018 by Mike Mirzayanov
    </footer>
    </body>
</@c.page>
