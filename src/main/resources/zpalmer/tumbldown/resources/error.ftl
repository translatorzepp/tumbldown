<head>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
    <title>Search tumblr error | tumbldown</title>
</head>
<body>
    <h1>tumbldown</h1>

    <small>:( something went wrong.</small>

    <#if error??>
        <p>${error.message}</p>
    </#if>
    <#if validationErrors??>
        <p>Your search terms were invalid.</p>
        <#list validationErrors as validationError><p>${validationError}</p></#list>
    </#if>

    <h3><a href="/search">Search again</a></h3>
</body>
