function changeSubscriptionUser(userIdToFollow) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/subscription/user/" + userIdToFollow,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            var buttonSubscription = $('button[name="subscription_button"]');
            if ($(buttonSubscription).text() == "Подписаться") {
                $(buttonSubscription).css('background-color','#28a745');
                $(buttonSubscription).text('Отписаться');
            } else {
                $(buttonSubscription).css('background-color','#007bff');
                $(buttonSubscription).text('Подписаться');
            }
        }
    })
}

function changeSubscriptionCar(userIdToFollow) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
        url : "/subscription/car/" + userIdToFollow,
        type : 'post',
        data: {_csrf : csrf},
        success : function() {
            var buttonSubscription = $('button[name="subscription_button_car"]');
            if ($(buttonSubscription).text() == "Подписаться") {
                $(buttonSubscription).css('background-color','#28a745');
                $(buttonSubscription).text('Отписаться');
            } else {
                $(buttonSubscription).css('background-color','#007bff');
                $(buttonSubscription).text('Подписаться');
            }
        }
    })
}