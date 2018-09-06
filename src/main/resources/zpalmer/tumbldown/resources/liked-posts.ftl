<body>
    <h1>Liked Posts</h1>

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
</body>
