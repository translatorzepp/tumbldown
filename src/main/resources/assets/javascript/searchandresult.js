function showSearchSpinner() {
    document.getElementById("searchingSpinner").removeAttribute("hidden");
}

function hideSearchSpinner() {
    document.getElementById("searchingSpinner").setAttribute("hidden", "hidden");
}

function search(blogName, searchText, beforeDate) {
    if (validateSearchInput(blogName, searchText)) {
        document.getElementById('searchResults').innerHTML = null;
        document.getElementById('errorMessage').innerHTML = null;

        // TODO: error handling
        var request = new XMLHttpRequest();
        var searchEndpoint = "/likes?blogName=" + blogName;
        if (searchText != null) {
            searchEndpoint += ("&searchText=" + searchText);
        }
        if (before != null) {
            searchEndpoint += ("&before=" + before + "&beforeTimezone=" + Intl.DateTimeFormat().resolvedOptions().timeZone);
        }

        request.open("GET", searchEndpoint, true);
        request.onprogress = function () {
            console.log("*** onprogress fired ***");
            document.getElementById('searchResults').innerHTML = request.responseText;
        }
        request.onload = function () {
            document.getElementById('searchResults').innerHTML = request.responseText;
            hideSearchSpinner();
            if (request.status != 200) {
                document.getElementById('errorMessage').innerHTML = request.errorMessage;
            }
        }
        request.onerror = function () {
            console.log(request.errorMessage);
            document.getElementById('errorMessage').innerHTML = "Something went wrong!";
            hideSearchSpinner();
        }
        request.onabort = function() {
            hideSearchSpinner();
        }

        showSearchSpinner();

        request.send();
    } else {
        alert("Specify the name of a blog to search!");
    }
}

function validateSearchInput(blog, text) {
    if (blog.length > 0) {
        return true;
    } else {
        return false;
    }
}
