
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>

${requestScope.contragentBarTop}
${requestScope.getContragentBarData}
${requestScope.tableTop}
${requestScope.tableHeadData}
<%-- ${requestScope.tableData} --%>
${requestScope.tableLow}

<script>
    function getEqModels(equipments) {
        let html = equipments.map((eq, index) => `
        <div class="equipment-view-block" data-index="` + index + `">
            <a class="eq-inline" href="/serialInfo?serial=` + encodeURIComponent(eq.serialNumber)+ `" target="_blank">
                <strong>` + eq.model + `</strong> / `+ eq.serialNumber + `
            </a>
                 <button type="button" class="btn btn-sm btn-outline-danger remove-equipment-btn">✖</button>
        </div>
    `).join('');
        html += `
        <button type="button" class="btn btn-xs btn-success add-equipment-btn">➕ Добавить оборудование</button>
    `;
        return html;
    }
    $(document).ready( function updateTable() {
        const tab = document.getElementById("reqTable");
        console.info(tab);
        $.ajax({
            type: "GET",
            url: `/rest/db?requestsToShow=`,
            success: function (message) {
                console.info(message);

                // const tab = document.getElementById("reqTable");

                for (let i = 0; i < message.length; i++) {
                    let row = document.createElement("tr");
                    row.innerHTML =
                        "<td>" + message[i].reqNumber + "</td>" +
                        "<td>" + message[i].customer + "</td>" +
                        "<td>" + message[i].customerPhone + "</td>" +
                        `<td class="wrap-text">` + message[i].address + `</td>` +
                        `<td class="wrap-text">` + getEqModels(message[i].equipmentsMontage) + `</td>` +
                        `<td class="wrap-text">` + getEqModels(message[i].equipmentsUnMontage) + `</td>` +
                        "<td>" + message[i].status + "</td>" +
                        "<td>" + message[i].createDate + "</td>" +
                        "<td>" + message[i].sla + "</td>" +
                        "<td>" + message[i].closeDate + "</td>" +
                        "<td>" + message[i].contragent + "</td>" +
                        "<td>" + message[i].user + "</td>" +
                        "<td>" + message[i].comment + "</td>" +
                        "<td>" + "</td>" +
                        `<td><button class="btn btn-primary ms-md-2 edit-btn">Изменить</button></td>`+
                        `<td><button class="btn btn-sm btn-danger close-btn" data-id=` + message[i].id + `>Закрыть</button></td>`;

                    row.setAttribute('data-eq-montage', encodeURIComponent(JSON.stringify(message[i].equipmentsMontage)));
                    row.setAttribute('data-eq-unmontage', encodeURIComponent(JSON.stringify(message[i].equipmentsUnMontage)));
                    tab.appendChild(row);
                }
      }
        });

        $(document).on('click', '.remove-equipment-btn', function () {
            const block = $(this).closest('.equipment-view-block');
            const index = parseInt(block.data('index'), 10); // индекс оборудования
            const row = $(this).closest('tr');
            const colIndex = block.closest('td').index();
            const attrName = colIndex === 4 ? 'data-eq-montage' : 'data-eq-unmontage';

            let data = decodeURIComponent(row.attr(attrName));
            let eqList = JSON.parse(data);
            eqList.splice(index, 1);

            row.attr(attrName, encodeURIComponent(JSON.stringify(eqList)));

            const newHtml = getEqModels(eqList);
            row.find('td').eq(colIndex).html(newHtml);
        });

    })

    $(document).on('click', '.close-btn', function () {
        const button = $(this);
        const id = button.data('id');
        const row = button.closest('tr');
        const reqNumber = row.find('td').eq(0).text().trim();

        const eqMontageJson = JSON.parse(decodeURIComponent(row.attr('data-eq-montage')));
        const eqUnmontageJson = JSON.parse(decodeURIComponent(row.attr('data-eq-unmontage')));
        function formatEquipmentList(equipments) {
            return equipments.map(eq => eq.model + ` / ` + eq.serialNumber ).join('\n');
        }

        const eqMontageText = formatEquipmentList(eqMontageJson);
        const eqUnmontageText = formatEquipmentList(eqUnmontageJson);


        const confirmed = confirm(`Вы точно хотите закрыть заявку № ` + reqNumber +  ` с оборудованием: `+
            `\n Установлено: \n` + eqMontageText +
            `\n--------------------------------------------` +
            `\n Снято:            \n` + eqUnmontageText);
        if (!confirmed) return;

        $.ajax({
            type: "GET",
            url: `rest/db?closeReq=` + id,
            success: function (response) {

                if (response.success === true) {
                    button.closest('tr').remove();
                } else {
                    alert("Не удалось закрыть заявку");
                }
            },
            error: function () {
                alert("Ошибка при закрытии заявки");
            }
        });
    });

    $(document).on('click', '.edit-btn', function () {
        const row = $(this).closest('tr');
        const button = $(this);

        if (button.text() === 'Изменить') {
            row.find('td').each(function (index) {
                const currentText = $(this).text().trim();

                if (index >= 0 && index < 13) {
                    if (index === 6) {
                        const cell = $(this);
                        // Показываем временно "Загрузка..."
                        cell.html('<select style="width: 100%"><option>Загрузка...</option></select>');

                        $.ajax({
                            url: '/statuses', // URL сервлета
                            method: 'GET',
                            success: function (statuses) {
                                let select = $('<select style="width: 100%"></select>');
                                statuses.forEach(status => {
                                    const option = $('<option></option>').val(status).text(status);
                                    if (status === currentText) {
                                        option.attr('selected', true);
                                    }
                                    select.append(option);
                                });
                                cell.html(select);
                            },
                            error: function () {
                                cell.html('<span style="color: red;">Ошибка загрузки</span>');
                            }
                        });
                    } else if (index === 4 || index === 5) {

                    } else {
                        $(this).html('<input type="text" style="width: 100%;">');
                        $(this).find('input').val(currentText);
                    }
                }
            });

            button.text('Сохранить');

        } else {
            row.find('td').each(function (index) {
                if (index >= 0 && index < 13) {
                    let newValue;
                    if (index === 4 || index === 5) {

                    } else if (index === 6) {
                        const selectedOption = $(this).find('select option:selected');
                        newValue = selectedOption.text();
                        $(this).text(newValue);
                    }else {
                        newValue = $(this).find('input').val();
                    }
                    $(this).text(newValue);
                }
            });

            button.text('Изменить');

            // TODO: AJAX сохранение
        }
    });

    $(document).on('click', '.add-equipment-btn', function () {
        const row = $(this).closest('tr');
        const rowIndex = row.index(); // можно сохранить index для связи с окном
        window.currentRowIndex = rowIndex;
        const left = (window.screen.width / 2) - (600 / 2);
        const top = (window.screen.height / 2) - (500 / 2);

        // открываем отдельное окно (или модалку, если нужно внутри)
        const win = window.open("/select-equipment?rowIndex=" + rowIndex, "selectEquipment", "width=600,height=500,left=" + left + ",top=" + top);
    });


    // document.addEventListener("DOMContentLoaded", function () {
    //     const header = document.getElementById("head");
    //     const navbar = document.getElementById("navbar");
    //     const tableHead =  document.getElementById("tableHead");
    //     const headerHeight = header.offsetHeight;
    //
    //     window.addEventListener("scroll", function () {
    //         if (window.scrollY > headerHeight) {
    //             navbar.style.position ="fixed";
    //             navbar.style.top = "0";
    //
    //
    //
    //         } else {
    //             navbar.style.position=("relative");
    //         }
    //     });
    // });
        </script>


    <%@include file="parts/footer.jsp" %>
