<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
        <script type="text/javascript" src="assets/javascript/searchandresult.js"></script>
        <title>tumbldown: search tumblr</title>
    </head>
    <body>
        <h1>tumbldown</h1>
        <h3>Search</h3>

        <div id="searchForm">
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
            <button onclick="search()">Search</button>
        </div>

        <div class="spinner" id="searchingSpinner" hidden="hidden"></div>
        <br/><br/><br/>

        <div class="error" id="errorMessage"></div>
        <div class="posts" id="searchResults"></div>
    </body>
</html>