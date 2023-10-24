function getModelByBrand() {

    var brandId = $('select[name="brand"] option:selected').val();

    $.ajax({
        url : "/news/models",
        type : 'get',
        data: {brandId : brandId},
        success : function(response) {
            $('select[name="model"] option').not('option:first').remove();
            for (var i = 0; i < response.length; i++){
                $('select[name="model"]').append('<option value="'+ response[i].id +'">' + response[i].description + '</option>');
            }
        }
    })
}

function getGenerationByBrand() {

    var modelId = $('select[name="model"] option:selected').val();

    $.ajax({
        url : "/news/generation",
        type : 'get',
        data: {modelId : modelId},
        success : function(response) {
            $('select[name="generation"] option').not('option:first').remove();
            for (var i = 0; i < response.length; i++){

            //todo проверить без пункта "Не выбрано
                if (response[i] != null) {
                    $('select[name="generation"]').append('<option value="'+ response[i].id +'">' + response[i].description + '</option>');
                } else {
                    $('select[name="generation"]').append('<option value="">Не выбрано</option>');
                }
            }
        }
    })
}

function paginat(param) {
    $('form[name="search"] input[name="pageNo"]').val($(param).attr("value"));

    $('form[name="search"]').submit();
}

function sendFilter(form) {
    $.ajax({
        url : $(form).attr('action'),
        type : 'get',
        data : $(form).serialize(),
        success : function(response) {
            $('div[name="posts"]').replaceWith(response);

//            $('select[name="brand"]').on("change", function (e) { getModelByBrand() });
//            $('select[name="model"]').on("change", function (e) { getGenerationByBrand() });
//
//            $('.select2').select2();
        }
    })
}

//function sendFilter(form) {
//    $.ajax({
//        url : $(form).attr('action'),
//        type : 'get',
//        data : $(form).serialize(),
//        success : function(response) {
//            $('div[name="posts"]').replaceWith(response);
//        }
//    })
//}