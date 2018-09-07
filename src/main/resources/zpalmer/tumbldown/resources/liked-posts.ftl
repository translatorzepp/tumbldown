<body>
    <h1>tumbldown</h1>

    <h3>Liked Posts</h3>
    <#list posts as post>
        <div>
            liked from <strong>${post.likedFromBlogName}</strong>:
            <a href="${post.url}" target="_blank">summary: ${post.summary}</a>
            <small><i>${post.type}</i></small>
            </br>
            tagged:<#list post.tags as tag> #${tag},<#else> no tags</#list>
        </div>
        <br></br>
    </#list>

    <a href="/search">Search again</a>
</body>
