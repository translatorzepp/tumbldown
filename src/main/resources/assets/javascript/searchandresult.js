function showSearchSpinner() {
    document.getElementById("searchingSpinner").removeAttribute("hidden");
}

function hideSearchSpinner() {
    document.getElementById("searchingSpinner").setAttribute("hidden", "hidden");
}

function search() {
    var blogName = document.getElementById("blogName").value;
    var searchText = document.getElementById("searchText").value;

    if (validateSearchInput(blogName, searchText)) {

        document.getElementById('searchResults').innerHTML = null;

        var xhr = new XMLHttpRequest();
        var searchEndpoint = "/likes?blogName=" + blogName + "&searchText=" + searchText;

        xhr.open("GET", searchEndpoint, true);
        xhr.onprogress = function () {
            document.getElementById('searchResults').innerHTML = xhr.responseText;
        }
        xhr.onload = function () {
            hideSearchSpinner();
        }
        .onerror = function () {
            console.log("** An error occurred during search **");
            hideSearchSpinner();
        }
        .onabort = function() {
            hideSearchSpinner();
        }

        showSearchSpinner();

        xhr.send();
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
