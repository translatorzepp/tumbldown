function showSearchSpinner() {
    document.getElementById('searching-spinner').removeAttribute('hidden');
}

function hideSearchSpinner() {
    document.getElementById('searching-spinner').setAttribute('hidden', 'hidden');
}

function search() {
    showSearchSpinner();

    var xhr = new XMLHttpRequest()
    xhr.open("GET", "/likes/chunked?blogName=ritterssport", true)
    xhr.onprogress = function () {
        document.getElementById('results').innerHTML = xhr.responseText;
    }
    xhr.onload = function () {
        hideSearchSpinner()
    }
    .onerror = function () {
        console.log("** An error occurred during search **");
        hideSearchSpinner()
    }
    .onabort = function() {
        hideSearchSpinner()
    }
    xhr.send()
}
