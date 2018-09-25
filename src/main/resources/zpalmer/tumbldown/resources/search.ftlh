<head>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"></link>
    <script type="text/javascript" src="assets/javascript/searchandresult.js"></script>
    <title>Search tumblr | tumbldown</title>
</head>
<body>
    <h1>tumbldown</h1>
    <h3>Search</h3>

    <div id="searchForm">
        <input type="text" id="blogName" name="blogName" placeholder="tumbldown-app" required></input>
        <input type="search" id="searchText" name="searchText" placeholder="thing to search for"></input>
        <button onclick="search()">Search</button>
    </div>
    <div class="spinner" id="searchingSpinner" hidden="hidden"></div>
    <br/>
    <br/>
    <br/>

    <div class="posts" id="searchResults"></div>
</body>
