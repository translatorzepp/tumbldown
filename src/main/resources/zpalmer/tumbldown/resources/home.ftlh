<#-- @ftlvariable type="zpalmer.tumbldown.resources.HomeView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: search tumblr</title>
    </head>

    <body>
        <h1>tumbldown</h1>

        <div>
            <p>An app for searching tumblr.</p>

            <#if likesSearchPath??>
                <a href="${likesSearchPath}">Search a tumblr's likes</a>
            </#if>
        </div>

        <#include "footer.ftlh">
    </body>
</html>