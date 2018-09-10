<head>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
    <title>Search tumblr results | tumbldown</title>
</head>
<body>
    <h1>tumbldown</h1>

    <#if originalSearch??><p>Search text: <strong>${originalSearch}</strong></p></#if>

    <h3>Liked Posts</h3>
    <#list posts as post>
        <div>
            liked from <strong>${post.likedFromBlogName}</strong>:
            <a href="${post.url}" target="_blank">summary: ${post.summary}</a>
            <small><i>${post.type}</i></small>
            </br>
            <div id="tags" style="text-indent:2em">tagged:<i><#list post.tags as tag> #${tag}</i><#else> no tags</#list></div>
        </div>
        <br></br>
    </#list>

    <h3><a href="/search">Search again</a></h3>
</body>
