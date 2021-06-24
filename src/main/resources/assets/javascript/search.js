function search() {
    showSearchSpinner();
    if (validateSearchInput(document.getElementById('blogName').value, document.getElementById('searchText').value)) {
        document.getElementById('errorMessage').innerHTML = null;

        var beforeTimestampSeconds = Math.round((new Date()).getTime() / 1000);

        var beforeDateElement = document.getElementById('beforeDate');
        var beforeDate = beforeDateElement.value;
        beforeTimestampSeconds = convertDateStringToEpochTimestampSeconds(beforeDate);

        document.getElementById('beforeTimestamp').value = beforeTimestampSeconds;

        document.getElementById('searchForm').submit;

    } else {
        alert("Specify the name of a blog to search!");
    }
}

function convertDateStringToEpochTimestampSeconds(dateString) {
    if (dateString != null && dateString != "") {
        var date = new Date(dateString);
        date.setHours(00);
        date.setMinutes(00);
        date.setSeconds(00);

        return Math.round(date.getTime() / 1000);
    } else {
        return Math.round((new Date()).getTime() / 1000);
    }
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
