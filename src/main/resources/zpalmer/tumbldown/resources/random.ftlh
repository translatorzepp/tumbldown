<#-- @ftlvariable type="zpalmer.tumbldown.resources.RandomView" -->
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
            <form action="/random/like" method="get">
                <label for="blogName">Get a random post liked by this blog</label>
                <br />
                <input type="text" id="blogName" name="blogName" autocapitalize="none" autocorrect="off" spellcheck="false" placeholder="tumbldown-app" required></input>

                <br /><br />
                <input type="submit" value="Randomize" />
            </form>
        </div>

        <br/>
        <div><a class="navigation" href="/">Back to home</a></div><br/>

        <#include "footer.ftlh">
    </body>
</html>