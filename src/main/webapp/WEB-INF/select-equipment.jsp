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

</script>
</body>
</html>