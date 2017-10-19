$(document).ready(function () {

    $("#get_weather_btn").click(function () {
        $.ajax('/weather', {
            type: 'GET',
            data: {
                cityId: $("#city_id").val()
            },
            success: function (t) {
                if (t !== "") {
                    $("#weather_temperature").text(t + " degrees Celsius")
                } else {
                    $("#weather_temperature").text("");
                }
            }
        });
    })
});
