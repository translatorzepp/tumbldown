<#-- @ftlvariable name="" type="zpalmer.tumbldown.core.LikesResultPageView" -->
<!-- TO DO: add a character encoding somewhere or other to prevent getting "The character encoding of the HTML document was not declared" console errors in firefox -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
        <title>tumbldown: searching likes</title>
    </head>
    <body>
        <h1>tumbldown</h1>
        <h3>Searching for posts liked by ${criteria.blogName}
            <#if criteria.searchText?has_content> containing ${criteria.searchText}</#if>
        </h3>

        <div class="posts" id="searchResults">
        <#list posts as post>

            <div class="post">
                liked from <strong>${post.likedFromBlogName}</strong>:

                <#if post.summary?has_content>
                   <a href="${post.url}" target="_blank">${post.summary}</a>
                <#elseif post.slug?has_content>
                    <a href="${post.url}" target="_blank">${post.slug}</a>
                <#else>
                    <a href="${post.url}" target="_blank">link</a>
                </#if>

                <small><i>${post.type}</i></small>
                </br>

                <div id="tags" style="text-indent:2em">
                    tagged:<#list post.tags as tag><i> #${tag}</i><#else> no tags</#list>
                </div>
            </div>
            <br/>

        </#list>
        </div>

        <div id="next_button_div">
        <form action="/search/likes" method="get">
            <input type="text" id="blogName" name="blogName" value=${criteria.blogName} required hidden></input>
            <input type="search" id="searchText" name="searchText" value="${criteria.searchText}" hidden></input>
            <input type="text" id="beforeTimestamp" name="beforeTimestamp" value="${criteria.nextBeforeTimestampSeconds}" hidden></input>
            <button type="submit">Next Page</button>
            </form>
            <!-- <button id="next_button" onclick="search()">Next Page</button> -->
        </div>

        <div class="spinner" id="searchingSpinner" hidden="hidden"></div>
    </body>
</html>