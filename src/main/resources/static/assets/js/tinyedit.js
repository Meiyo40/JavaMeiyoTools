$(document).ready(function() {
    function getPostContent(postId) {
        let url = "index.php?action=getArticleContent&article=" + postId;
        $.post(url, null, function(data) {
            data = JSON.parse(data);
            updateEditor(data);
        });
    }

    async function updateEditor(article) {
        let title = document.getElementById('article-title');
        title.value = article.title;
        await sleep(500);
        let content = tinymce.activeEditor.getBody();;
        content.innerHTML = article.content;
    }

    let $_GET = [];
    let parts = window.location.search.substr(1).split("&");
    for (let i = 0; i < parts.length; i++) {
        let temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }

    if ($_GET.article) {
        getPostContent($_GET.article);
    }

    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }


});