$(document).ready(function() {
//    fillBaseURL()
    var URL = "http://localhost:8080/airhermes/discounts";

    var table = $("table#discountsViewTable");
    var inputCoefficient = table.find("input[name=coefficient]");
    var inputDate = table.find("input[name=validUntilDate]");
    var lastRowPost = table.find("tr:last");


    function fillDiscounts() {
        $.get(URL + "/async", null, function(response) {
            console.log(response);

            if (response.status == "ok") {
                table.find("tr:not(:last)").remove();

                var discounts = response.discounts
                for (var itDiscount in discounts) {
                    lastRowPost.before(
                        '<tr>' +
                            '<td>' + (discounts[itDiscount].discountCoefficient * 100) + '%</td>' +
                             '<td>' + discounts[itDiscount].validUntilDate + '</td>' +
                             '<td></td>' +
                        '</tr>'
                    )

                }

            }

        })

    }
    fillDiscounts();



    function postDiscount() {
        var coefficient = inputCoefficient.val();
        var date = inputDate.val();

        var parameters = {
            coefficient: coefficient/100,
            validUntilDate: date
        }
        console.log(parameters);

        $.post(URL, parameters, function(response) {
                    console.log(response);

                    if (response.status == "created") {
                        let discount = response.discount;

                        lastRowPost.before(
                                '<tr>' +
                                    '<td>' + (discount.discountCoefficient * 100) + '%</td>' +
                                     '<td>' + discount.validUntilDate + '</td>' +
                                     '<td></td>' +
                                '</tr>'
                            );
                        $("p#greskaPopust").textContent = '';
                    }
                    if (response.status == "bad request") {
                        $("p#greskaPopust").textContent = 'Error 400';
                    }

                })
    }


    $("form#discountAddForm").submit(function() {
        postDiscount()
        return false
    })
})