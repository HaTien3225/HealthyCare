
function setPage(change) {
    const pageInput = document.getElementById('pageInput');
    let currentPage = parseInt(pageInput.value) || 1;
    currentPage += change;
    if (currentPage < 1)
        currentPage = 1;
    pageInput.value = currentPage;
}


