<#-- @ftlvariable type="zpalmer.tumbldown.core.SearchCriteria" -->
<!-- TO DO: add a character encoding somewhere or other to prevent getting "The character encoding of the HTML document was not declared" console errors in firefox -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
        <script type="text/javascript" src="assets/javascript/searchandresult.js"></script>
        <title>tumbldown: search tumblr</title>
    </head>
    <body>
        <h1>tumbldown</h1>
        <h3>Search</h3>

         <!-- TO DO: pass in an object that holds search criteria; if it exists, trigger the search -->

        <div id="searchForm">
            <form action="/search" method="get">
                <label for="blogName">The blog whose likes you want to search</label>
                <br />
                <input type="text" id="blogName" name="blogName" placeholder="tumbldown-app" required></input>

                <br /><br />
                <label for="searchText"><small>(optional)</small> What to search for, in tags and summary</label>
                <br />
                <input type="search" id="searchText" name="searchText" placeholder="thing to search for"></input>

                <br /><br />
                <label for="before"><small>(optional)</small> Search before this date</label>
                <br />
                <input type="date" id="before" name="before"></input>

                <br /><br />
                <button type="submit">Search</button>
            </form>
        </div>

        <div class="spinner" id="searchingSpinner" hidden="hidden"></div>
        <br/><br/><br/>

        <div class="error" id="errorMessage"></div>
        <div class="posts" id="searchResults"></div>
    </body>

    <#if searchCriteria??>
    <script>search("${searchCriteria.blogName}", "${searchCriteria.searchText}", ${searchCriteria.beforeTimestampSeconds})</script>
    </#if>
</html>