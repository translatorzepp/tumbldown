<#-- @ftlvariable type="zpalmer.tumbldown.resources.ErrorView" -->
<div>
    <small>:( something went wrong.</small>

    <#if error??>
        <p>${error.message}</p>
    </#if>
    <#if validationErrors??>
        <p>Your search terms were invalid.</p>
        <#list validationErrors as validationError><p>${validationError}</p></#list>
    </#if>

    <a href="/search">Back to search</a>
</div>
