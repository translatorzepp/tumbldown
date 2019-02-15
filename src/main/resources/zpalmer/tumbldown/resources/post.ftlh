<#-- @ftlvariable name="" type="zpalmer.tumbldown.resources.SinglePostView" -->
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
    <br/>
</div>
