<#-- @ftlvariable name="" type="zpalmer.tumbldown.core.SearchView" -->
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css"></link>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>tumbldown: search tumblr</title>
        <script type="text/javascript" src="/assets/javascript/search.js"></script>
    </head>

    <body>
        <h1>tumbldown</h1>
        <h3>Search Likes</h3>

        <div id="searchForm">
            <form action="/search/likes" method="get">
                <label for="blogName">The blog whose likes you want to search</label>
                <br />
                <input type="text" id="blogName" name="blogName" autocapitalize="none" autocorrect="off" spellcheck="false" placeholder="tumbldown-app" required></input>

                <br /><br />
                <label for="searchText"><small>(optional)</small> What to search for, in tags and summary</label>
                <br />
                <input type="search" id="searchText" name="searchText" autocapitalize="none" autocorrect="off" spellcheck="false" placeholder="thing to search for"></input>

                <br /><br />
                <label for="before"><small>(optional)</small> Search before this date</label>
                <br />
                <input type="date" id="beforeDate"></input>

                <br /><br />
                <label for="postTypes"><small>(optional)</small> Search for posts of this type</label>
                <br />
                <input type="checkbox" name="postTypes" value="photo">photo</input>
                <input type="checkbox" name="postTypes" value="video">video</input>
                <input type="checkbox" name="postTypes" value="audio">audio</input>
                <input type="checkbox" name="postTypes" value="text">text</input>
                <input type="checkbox" name="postTypes" value="quote">quote</input>
                <input type="checkbox" name="postTypes" value="link">link</input>
                <input type="checkbox" name="postTypes" value="chat">chat</input>
                <input type="checkbox" name="postTypes" value="answer">answer</input>

                <input type="text" id="beforeTimestamp" name="beforeTimestamp" hidden></input>

                <br /><br />
                <button onclick="search()">Search</button>
            </form>
        </div>

        <div class="spinner" id="searchingSpinner" hidden="hidden"></div>

        <div class="error" id="errorMessage"></div>
    </body>
</html>