<#-- @ftlvariable name="" type="zpalmer.tumbldown.core.RandomLikedPostView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: random like</title>
    </head>

    <script type="text/javascript">
        function convertEpochTimestampSecondsToDate(timestamp) {
            timestampMilliseconds = parseInt(timestamp) * 1000;

            if (Number.isNaN(timestampMilliseconds)) {
                return null;
            } else {
                var date = new Date(timestampMilliseconds);
                return date.toLocaleString();
            }
        }

        function convertDates() {
            var dateElements = document.getElementsByClassName("dateInSeconds")
            var i;
            for (i = 0; i < dateElements.length; i++) {
                var dateElement = dateElements[i];
                dateElement.innerHTML = convertEpochTimestampSecondsToDate(dateElement.getAttribute("value"));
            }
        }
    </script>

    <body onload="convertDates()">
        <h1>tumbldown</h1>

            <div class="post">
                liked from <strong>${post.likedFromBlogName}</strong>:

                <#if post.summary?has_content>
                   <a href="${post.url}" target="_blank" rel="noopener noreferrer">${post.summary}</a>
                <#elseif post.slug?has_content>
                    <a href="${post.url}" target="_blank" rel="noopener noreferrer">${post.slug}</a>
                <#else>
                    <a href="${post.url}" target="_blank" rel="noopener noreferrer">link</a>
                </#if>

                <small><i>${post.type}</i></small>
                <br/>

                <div class="tags" style="text-indent:2em">
                    tagged:<#list post.tags as tag><i> #${tag}</i><#else> no tags</#list>
                </div>

                <#if post.likedAtForDisplay?has_content>
                <div style="text-indent:2em">
                    liked at: <span class="dateInSeconds" value="${post.likedAtForDisplay}"></span>
                </div>
                </#if>
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
        <div><a class="navigation" href="/random">Randomize again?</a></div><br/>
        <div><a class="navigation" href="/">Back to home</a></div><br/>

        <#include "footer.ftlh">
    </body>
</html>
