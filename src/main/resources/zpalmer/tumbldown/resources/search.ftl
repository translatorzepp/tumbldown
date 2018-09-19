<head>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
    <title>Search tumblr | tumbldown</title>
</head>
<body>
    <h1>tumbldown</h1>
    <h3>Search</h3>

    <div id="search-form">
        <form action="/likes" method="get" onsubmit="document.getElementById('searching-spinner').removeAttribute('hidden');">
            <input type="text" id="blogName" name="blogName" placeholder="tumbldown-app" required></input>
            <input type="search" id="searchText" name="searchText" placeholder="thing to search for"></input>
            <button type="submit">Search</button>
        </form>
    </div>
    <div class="spinner" id="searching-spinner" hidden="hidden"></div>
</body>
