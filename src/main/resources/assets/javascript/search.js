function search() {
    showSearchSpinner();
    if (validateSearchInput(document.getElementById('blogName').value, document.getElementById('searchText').value)) {
        document.getElementById('errorMessage').innerHTML = null;

        var beforeTimestampSeconds = Math.round((new Date()).getTime() / 1000);
        document.getElementById('beforeTimestamp').value = beforeTimestampSeconds;

        document.getElementById('searchForm').submit;

    } else {
        alert("Specify the name of a blog to search!");
    }
}

function convertDateToEpochTimestampSeconds(date) {
    return Math.round((new Date()).getTime() / 1000);
}

function convertEpochTimestampSecondsToDate(timestamp) {
    return timestamp.toLocaleDateString();
}

function validateSearchInput(blog, text) {
    if (blog.length > 0) {
        return true;
    } else {
        return false;
    }
}

function showSearchSpinner() {
    document.getElementById("searchingSpinner").removeAttribute("hidden");
}

function hideSearchSpinner() {
    document.getElementById("searchingSpinner").setAttribute("hidden", "hidden");
}
