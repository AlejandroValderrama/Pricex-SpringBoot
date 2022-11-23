$(function () {
    $('#pagination').pagination({
        items: document.querySelector('#totalPage').value,
        itemOnPage: 10,
        currentPage: document.querySelector('#current').value,
        cssStyle: '',
        prevText: '<span aria-hidden="true">&laquo;</span>',
        nextText: '<span aria-hidden="true">&raquo;</span>',
        hrefTextPrefix: '?page=',
        onPageClick: function (page, evt) {
            var path = window.location.pathname;
            window.location.href = path+"?page="+page;
        }
    });
});
    







