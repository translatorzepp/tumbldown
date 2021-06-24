<#-- @ftlvariable type="zpalmer.tumbldown.resources.ErrorView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: error</title>
    </head>

    <body>
        <h1>tumbldown</h1>

        <div>
            <small>ಠ_ಠ something went wrong.</small>

            <#if error??>
                <p>${error.message}</p>
            </#if>
            <#if validationErrors??>
                <p>Your search terms were invalid.</p>
                <#list validationErrors as validationError><p>${validationError}</p></#list>
            </#if>

            <div><a class="navigation" href="/search">Back to search</a></div><br/>
            <div><a class="navigation" href="/random">Back to random</a></div><br/>
            <div><a class="navigation" href="/">Back to home</a></div><br/>
        </div>

        <#include "footer.ftlh">
    </body>
</html>