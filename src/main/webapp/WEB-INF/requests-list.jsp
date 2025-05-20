<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>

${requestScope.contragentBarTop}
${requestScope.getContragentBarData}
${requestScope.tableTop}
${requestScope.tableHeadData}
<%-- ${requestScope.tableData} --%>
${requestScope.tableLow}

<script>

    $(document).ready( function updateTable() {
        const tab = document.getElementById("reqTable");
        console.info(tab);
        $.ajax({
            type: "GET",
            url: `/rest/db?requestsToShow=${requestsToShow}`,
            success: function (message) {
                console.info(message);

                // const tab = document.getElementById("reqTable");

                for (let i = 0; i < message.length; i++) {
                    let row = document.createElement("tr");
                    row.innerHTML =
                        "<td>" + message[i].reqNumber + "</td>" +
                        "<td>" + message[i].customer + "</td>" +
                        "<td>" + message[i].customerPhone + "</td>" +
                        "<td>" + message[i].address + "</td>" +
                        "<td>" + getEqModels(message[i].equipmentsMontage) + "</td>" +
                        "<td>" + getEqSerials(message[i].equipmentsMontage) + "</td>" +
                        "<td>" + getEqModels(message[i].equipmentsUnmontage) + "</td>" +
                        "<td>" + getEqSerials(message[i].equipmentsUnmontage) + "</td>" +
                        "<td>" + message[i].status + "</td>" +
                        "<td>" + message[i].createDate + "</td>" +
                        "<td>" + message[i].sla + "</td>" +
                        "<td>" + message[i].closeDate + "</td>" +
                        "<td>" + message[i].contragent + "</td>" +
                        "<td>" + message[i].user + "</td>" +
                        "<td>" + message[i].comment + "</td>" +
                        "<td>" + "</td>" +
                        `<td><button class="edit-btn">Редактировать</button></td>`;

                    ;


                    tab.appendChild(row);
                }
                $('#reqTable').on('click', '.edit-btn', function () {
                    const row = $(this).closest('tr');
                    const button = $(this);

                    if (button.text() === 'Редактировать') {
                        row.find('td').each(function (index) {
                            // Пропускаем последний столбец (кнопку)
                            if (index < 15) {
                                const currentText = $(this).text();
                                $(this).html(`<input type="text" value=` + currentText + `/>`);
                            }
                        });
                        button.text('Сохранить');
                    } else {
                        row.find('td').each(function (index) {
                            if (index < 15) {
                                const newValue = $(this).find('input').val();
                                $(this).text(newValue);
                            }
                        });
                        button.text('Редактировать');

                        // TODO: здесь можно отправить AJAX-запрос на сохранение изменений
                    }
                });


            }
        });
        function getEqModels(arr){
            let equipment= "";
            for (let i = 0; i <arr.length; i++) {

                equipment = equipment + arr[i].model
                if (i != arr.length-1){
                    equipment = equipment + " / "
                }

            }
            return equipment;

        }
        function getEqSerials(arr){
            let equipment= "";
            for (let i = 0; i <arr.length; i++) {

                equipment = equipment + arr[i].serialNumber
                if (i != arr.length-1){
                    equipment = equipment + " / "
                }
            }
            return equipment;

        }
    })




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
