<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выбор оборудования</title>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>
<h1>Выберите контрагента:</h1>
<select style="width: 100%" id = "contragent"><option>Выбор</option></select>

<h1>Выберите оборудование:</h1>

<select id="select-equipment" style="width: 100%"></select>
<button id="ok-button">ОК</button> <button>Отмена</button>
<script>
    $('#select-equipment').select2({
        placeholder: 'Введите модель или серийник...',
        ajax: {
            url: '/rest-equipment',
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    equipment: params.term || '',
                    contragent: $('#contragent').val()
                };
            },
            processResults: function (data) {
                return {
                    results: data.map(function(item) {
                        return {
                            id: item.serialNumber,
                            text: item.model + ' (' + item.serialNumber + ')'
                        };
                    })
                };
            },
            cache: true
        },
        minimumInputLength: 0
    });

    $('#contragent').on('change', function () {
        $('#select-equipment').val(null).trigger('change'); // сбросить текущий выбор
    });



    $(document).ready(function() {
        $.ajax({
            url: '/get-contragents',
            method: 'GET',
            success: function(contragents) {
                let select = $('#contragent');
                select.empty();
                select.append('<option value="">Выбор</option>');

                contragents.forEach(contragent => {
                    const option = $('<option></option>').val(contragent).text(contragent);
                    select.append(option);
                });
            },
            error: function() {
                alert('Ошибка загрузки контрагента');
            }
        });
    });
    $('#ok-button').on('click', function () {
        const selectedData = $('#select-equipment').select2('data');

        if (!selectedData || selectedData.length === 0) {
            alert("Выберите оборудование!");
            return;
        }
        const item = selectedData[0];
        const model = item.text.split(' (')[0];
        const serialNumber = item.id;

        returnEquipmentToMainPage(model, serialNumber);
    });


    function returnEquipmentToMainPage(model, serialNumber) {
        if (window.opener && !window.opener.closed) {
            const urlParams = new URLSearchParams(window.location.search);
            const rowIndex = urlParams.get("rowIndex");
            const colIndex = urlParams.get("colIndex");

            window.opener.receiveSelectedEquipment({
                model: model,
                serialNumber: serialNumber,
                rowIndex: parseInt(rowIndex),
                colIndex: parseInt(colIndex)
            });

            window.close();
        }
    }
    $('button:contains("Отмена")').on('click', function () {
        window.close();
    });
</script>
</body>
</html>