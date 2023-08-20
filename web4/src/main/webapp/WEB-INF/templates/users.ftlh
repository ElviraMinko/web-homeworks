<#import "./common.ftlh" as c>

<@c.page>
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="css/style3.css">
    </head>
    <body>
    <div class="middle">
            <div class="datatable">
                <div class="caption">USERS</div>
                <table>
                    <thead>
                    <tr>
                        <th>User</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list users as user>
                        <tr>
                            <td><a href="/user?handle=${user.handle}">${user.name}</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
    </div>
    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2018 by Mike Mirzayanov
    </footer>
    </body>
</@c.page>