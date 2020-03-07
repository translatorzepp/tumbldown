<#-- @ftlvariable name="" type="zpalmer.tumbldown.core.LikesResultPageView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <script type="text/javascript" src="/assets/javascript/results.js"></script>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: searching likes</title>
    </head>

    <body onload="displayDatesFromCriteria()">
        <h1>tumbldown</h1>
        <div id="search_criteria">
            Searching for posts liked by <em>${criteria.blogName}</em>
            <#if criteria.searchText?has_content><span> containing <em>${criteria.searchText}</em></span></#if>
            <span <#if criteria.currentBeforeTimestampSeconds?has_content><#else>hidden</#if>>
                <span> between
                <em>
                    <span id="search_criteria_after"
                    timestamp=${criteria.nextBeforeTimestampSeconds!""}></span>
                </em> and <em>
                    <span id="search_criteria_before" timestamp=${criteria.currentBeforeTimestampSeconds}></span>
                </em>
                </span>
            </span>
        </div>

        <br/><br/>
        <div class="posts" id="search_results">
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
                <br/>

                <div id="tags" style="text-indent:2em">
                    tagged:<#list post.tags as tag><i> #${tag}</i><#else> no tags</#list>
                </div>
                <br/>
            </div>
            <br/>

        <#else>
        No posts found in this interval.
        <br/><br/>
        </#list>
        </div>

        <div id="next_button_div">
            <form id="next_button_form" action="/search/likes" method="get">
                <input hidden type="text" id="blogName" name="blogName" value=${criteria.blogName}></input>
                <input hidden type="search" id="searchText" name="searchText" value="${criteria.searchText}"></input>
                <input hidden type="text" id="beforeTimestamp" name="beforeTimestamp" value=${criteria.nextBeforeTimestampSeconds!""}></input>

                <button <#if criteria.nextBeforeTimestampSeconds??><#else>disabled</#if> onclick="nextPage()">Next Page</button>
            </form>
        </div>

        <#if criteria.nextBeforeTimestampSeconds??>
        <#else>
        <br/><br/>
        No more likes found.
        <br/><br/>
        </#if>

        <br/>
        <div class="spinner" id="searchingSpinner" hidden="hidden"></div>
        <br><br/><br><br/><br><br/><br><br/>
        <a href="/search">New search?</a>
    </body>
</html>