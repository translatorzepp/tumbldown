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
        <small>:( something went wrong.</small>

        <#if error??>
            <p>${error.message}</p>
        </#if>
        <#if validationErrors??>
            <p>Your search terms were invalid.</p>
            <#list validationErrors as validationError><p>${validationError}</p></#list>
        </#if>

        <a href="/search">Back to search</a>
    </div>
    </body>
</html>