<#-- @ftlvariable name="" type="zpalmer.tumbldown.core.RandomLikedPostView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: random like</title>
    </head>

    <body>
        <h1>tumbldown</h1>

            <div class="post">
                <#if post.likedFromBlogName?has_content>
                    liked from <strong>${post.likedFromBlogName}</strong>:
                </#if>

                <#if post.summary?has_content>
                   <a href="${post.url}" target="_blank">${post.summary}</a>
                <#elseif post.slug?has_content>
                    <a href="${post.url}" target="_blank">${post.slug}</a>
                <#else>
                    <a href="${post.url}" target="_blank">link</a>
                </#if>

                <small><i>${post.type}</i></small>
                <br/>

                <div class="tags" style="text-indent:2em">
                    tagged:<#list post.tags as tag><i> #${tag}</i><#else> no tags</#list>
                </div>
                <br/>
            </div>

            <br/>

        <div id="random_again">
            <form action="/random/like" method="get">
                <input hidden type="text" id="blogName" name="blogName" value=${blogName}></input>
                <input type="submit" value="Randomize again" />
            </form>
        </div>

        <br/><br/><br/>
        <a href="/random">Go back</a>

        <#include "footer.ftlh">
    </body>
</html>