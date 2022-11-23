/*--------------------------------------------------------------
# Grafica
--------------------------------------------------------------*/
window.addEventListener('load', getChartData, false);


function getChartData() {
    var path = window.location.pathname;

    $.ajax({
        url: path + "/getData",
        dataType: 'json',
        success: function (result) {
            var data = [];
            data.push(result.precio);
            fechas = result.fecha;

            renderChart(data, fechas);
        }
    });
}


function renderChart(data, fechas) {
    var ctx = document.getElementById("myChart").getContext('2d');

    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: fechas,
            datasets: [
                {
                    label: 'precio',
                    data: data[0],
                    borderColor: 'rgba(255,55,57,0.4)',
                }
            ]
        },
        options: {
            tooltips: {
                xPadding: 15,
                yPadding: 15,
                bodySpacing: 15,
            },
            layout: {
                padding: {

                }
            },
            elements: {
                line: {
                    borderwidth: 8,
                    fill: false,
                },
                point: {
                    radius: 5,
                    borderWidth: 3,
                    backgroundColor: 'rgba(255,255,255)',
                    hoverRadius: 8,
                    hoverBorderWidth: 3,
                }
            },
            scales: {
                yAxes: [{
                    ticks: {
                        suggestedMin: 0,
                    }
                }],
                xAxes: [{
                    gridLines: {
                        /* display: false, */
                    }
                }]
            }
        }
    });
}





