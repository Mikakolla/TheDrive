function addLike(i, postId) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/post/like/" + postId,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            $(i).removeClass('fa-heart-o').addClass('fa fa-heart');
            $(i).css('color', '#cb0000')
            var countLike = parseInt($(i).parent().find('span[name="countLike"]').text()) + 1;
            $(i).parent().find('span[name="countLike"]').text(countLike);
            $(i).attr('onclick', 'removeLike($(this),'+ postId +')');
        }
    })
}

function removeLike(i, postId) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/post/like_remove/" + postId,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            $(i).removeClass('fa fa-heart').addClass('fa fa-heart-o');
            $(i).css('color', 'gray')
            var countLike = parseInt($(i).parent().find('span[name="countLike"]').text()) - 1;
            $(i).parent().find('span[name="countLike"]').text(countLike);
            $(i).attr('onclick', 'addLike($(this),'+ postId +')');
        }
    })
}

function addBookmark(i, postId) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/post/bookmark/" + postId,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            $(i).removeClass('fa-bookmark-o').addClass('fa fa-bookmark');
            $(i).css('color', '#ffc107')
            $(i).attr('onclick', 'removeBookmark($(this),'+ postId +')');
        }
    })
}

function removeBookmark(i, postId) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/post/bookmark_remove/" + postId,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            $(i).removeClass('fa fa-bookmark').addClass('fa fa-bookmark-o');
            $(i).css('color', 'gray')
            $(i).attr('onclick', 'addBookmark($(this),'+ postId +')');
        }
    })
}