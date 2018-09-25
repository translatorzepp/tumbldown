<#-- @ftlvariable name="" type="zpalmer.tumbldown.resources.SinglePostView" -->
<div>
    liked from <strong>${post.likedFromBlogName}</strong>:
    <a href="${post.url}" target="_blank">summary: ${post.summary}</a>
    <small><i>${post.type}</i></small>
    </br>
    <div id="tags" style="text-indent:2em">
        tagged:<#list post.tags as tag><i> #${tag}</i><#else> no tags</#list>
    </div>
    <br/>
</div>
