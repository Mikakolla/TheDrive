function changeCheckbox(checkbox) {

    var value = $(checkbox).val();

    if (value == 'true') {
        $(checkbox).val('');
    } else {
        $(checkbox).val('true');
    }
}

//profile
function addCategoryId(option) {

    var categoryId = $(option).attr('data-value');
    $('input#category').val(categoryId);
    console.log("$('input#category') = ", $('input#category').val())
}