function nextPage() {
    showSearchSpinner();
    document.getElementById("next_button_form").submit;
}

function displayDatesFromCriteria() {
    var beforeSearchCriteriaDateElement = document.getElementById("search_criteria_before");
    var beforeTimestamp = beforeSearchCriteriaDateElement.getAttribute("timestamp");
    var beforeTimestampString = convertEpochTimestampSecondsToDate(beforeTimestamp);
    if (beforeTimestampString != null) {
        beforeSearchCriteriaDateElement.innerHTML = beforeTimestampString;
    }

    var afterSearchCriteriaDateElement = document.getElementById("search_criteria_after");
    var afterTimestamp = afterSearchCriteriaDateElement.getAttribute("timestamp");
    var afterTimestampString = convertEpochTimestampSecondsToDate(afterTimestamp);
    if (afterTimestampString != null) {
        afterSearchCriteriaDateElement.innerHTML = afterTimestampString;
    } else {
        afterSearchCriteriaDateElement.innerHTML = "end of likes search"
    }
}

function convertEpochTimestampSecondsToDate(timestamp) {
    timestampMilliseconds = parseInt(timestamp) * 1000;

    if (Number.isNaN(timestampMilliseconds)) {
        return null;
    } else {
        var date = new Date(timestampMilliseconds);
        return date.toLocaleString();
    }
}

function showSearchSpinner() {
    document.getElementById("searchingSpinner").removeAttribute("hidden");
}
