<head>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
</head>
<body>
    <h1>tumbldown</h1>
    <h3>Search</h3>

    <form action="/likes" method="get">
        <input type="text" id="blogName" name="blogName" placeholder="tumbldown-app" required></input>
        <input type="search" id="searchText" name="searchText" placeholder="thing to search for"></input>
        <button type="submit">Search</button>
    </form>
    <br></br>
    <div class="spinner" hidden="hidden"></div>
</body>
