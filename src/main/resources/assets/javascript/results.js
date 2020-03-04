function nextPage() {
    showSearchSpinner();
    document.getElementById("next_button_form").submit;
}

function displayDatesFromCriteria() {
    var beforeSearchCriteriaDateElement = document.getElementById("search_criteria_before");
    var beforeTimestamp = beforeSearchCriteriaDateElement.getAttribute("timestamp");
    if (beforeTimestamp != null) {
        beforeSearchCriteriaDateElement.innerHTML = convertEpochTimestampSecondsToDate(beforeTimestamp);
    }

    var afterSearchCriteriaDateElement = document.getElementById("search_criteria_after");
    var afterTimestamp = afterSearchCriteriaDateElement.getAttribute("timestamp");
    if (afterTimestamp != null) {
        afterSearchCriteriaDateElement.innerHTML = convertEpochTimestampSecondsToDate(afterTimestamp);
    }
}

function convertEpochTimestampSecondsToDate(timestamp) {
    timestampMilliseconds = parseInt(timestamp) * 1000;
    var date = new Date(timestampMilliseconds);
    return date.toLocaleString();
}

function showSearchSpinner() {
    document.getElementById("searchingSpinner").removeAttribute("hidden");
}
