function changeFormEditUser() {
    var form = $('form[name="editUser"]');
    $(form).find('input').removeAttr('disabled')
    $(form).find('button').css('display', 'block')
    $(form).find('input#password').attr('type', 'text')
    $(form).find('label[for="password"]').css('display', 'block')
}

function changeFormEditCar() {
    var form = $('form[name="editCar"]');
    $(form).find('input').removeAttr('disabled')
    $(form).find('button').css('display', 'block')
}

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

//function getModelByBrandForEditCar(carModelId) {
//
//    console.log("carModelId = ", carModelId)
//
//    var brandId = $('select[name="brand"] option:selected').val();
//
//    $.ajax({
//        url : "/news/models",
//        type : 'get',
//        data: {brandId : brandId},
//        success : function(response) {
//            $('select[name="model"] option').not('option:first').remove();
//            for (var i = 0; i < response.length; i++){
//                $('select[name="model"]').append('<option value="'+ response[i].id +'">' + response[i].description + '</option>');
//            }
//        }
//    })
//}

function getGenerationByBrand() {

    var modelId = $('select[name="model"] option:selected').val();

    $.ajax({
        url : "/news/generation",
        type : 'get',
        data: {modelId : modelId},
        success : function(response) {
            $('select[name="generation"] option').not('option:first').remove();
            for (var i = 0; i < response.length; i++){
                if (response[i] != null) {
                    $('select[name="generation"]').append('<option value="'+ response[i].id +'">' + response[i].description + '</option>');
                } else {
                    $('select[name="generation"]').append('<option value="">Не выбрано</option>');
                }
            }
        }
    })
}

function saveEditUser(form) {
    $.ajax({
            url : "/user/save",
            type : 'post',
            data : $(form).serialize(),
            success : function(response) {
            console.log("response = " ,response)
                $('div[name="userForm"]').replaceWith(response);
            }
        })
}

function paginat(param) {
    $('form[name="pagination"] input[name="pageNo"]').val($(param).attr("value"));

    $('form[name="pagination"]').submit();
}

function sendFilter(form) {
    $.ajax({
        url : $(form).attr('action'),
        type : 'get',
        data : $(form).serialize(),
        success : function(response) {
            $('div[name="posts"]').replaceWith(response);

            $('select[name="brand"]').on("change", function (e) { getModelByBrand() });
            $('select[name="model"]').on("change", function (e) { getGenerationByBrand() });

            $('.select2').select2();
        }
    })
}

function deleteService(serviceId) {

    var csrf = $('input[name="_csrf"]').val();

    $.ajax({
            url : "/service/delete",
            type : 'post',
            data : {_csrf : csrf, serviceId : serviceId},
            success : function() {
            }
        })
}

function initFlatpickr() {

    var disabledDay = [];

    $('span.day-work').each(function( index ) {
        if ($(this).text() == 'Выходной') {
            disabledDay.push($( this ).attr('value'));
        }
    });

    $("#date").flatpickr({
        dateFormat: "d.m.Y",
        minDate: "today",
        "disable": [
            function(date) {
                return disabledDay.includes(date.getDay().toString());
            }
        ],
        "locale": {
            "firstDayOfWeek": 1 // set start day of week to Monday
        }
    });
}

function getFreeTimeForNewRecord() {
    var serviceId = $('select[name="serviceId"] option:selected').val();
    var date = $('input[name="date"]').val();

    $.ajax({
            url : "/service/free_time",
            type : 'get',
            data : {date : date, serviceId : serviceId},
            success : function(response) {

                $('div#time').children().remove();

                if (response.length > 0) {
                    for (var i = 0; i < response.length; i++){
                        $('div#time').append('<input type="radio" class="btn-check" name="time" id="success-outlined'+ i + '" value="' + response[i] + ':00" autocomplete="off" required> ' +
                                             '<label class="btn btn-outline-primary" for="success-outlined'+ i + '">' + response[i] + ':00 </label>');
                    }
                } else {
                    $('div#time').append('<p style="color: #919191; margin: 0">Нет свободного времени на указанную дату</p>')
                }
            }
        })
}

function changeStatusUseServices(select) {

    var csrf = $('input[name="_csrf"]').val();
    var useServicesId = $(select).attr('id');
    var selectedValue = $(select).find('option:selected').val()

        $.ajax({
                url : "/use_services/change",
                type : 'post',
                data : {_csrf : csrf, useServiceId : useServicesId, status : selectedValue},
                success : function() {
                    if (selectedValue == "AWAIT") {
                        $(select).parent().find('i').css('color', '#17a2b8');
                    } else if (selectedValue == "IN_PROCESS") {
                        $(select).parent().find('i').css('color', '#ffd862');
                    } else if (selectedValue == "DONE") {
                        $(select).parent().find('i').css('color', '#2dbf4e');
                    }
                }
            })

}
