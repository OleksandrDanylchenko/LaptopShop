<#macro pieGraph collapseId graphContainer titleText units dataPoints>
    <script>
        $('#${collapseId}').one('shown.bs.collapse', function () {
            const dps = [];
            const chart = new CanvasJS.Chart("${graphContainer}", {
                theme: "light1",
                exportEnabled: true,
                animationEnabled: true,
                title: {
                    text: "${titleText}",
                    fontFamily: "Lato"
                },
                legend: {
                    fontFamily: "Lato"
                },
                toolTip: {
                    fontFamily: "Lato"
                },
                data: [{
                    type: "pie",
                    showInLegend: "true",
                    legendText: "{label}",
                    indexLabelFontSize: 16,
                    indexLabel: "{label} - {y} ${units}",
                    toolTipContent: "{y} ${units} - #percent %",
                    dataPoints: dps
                }]
            });

            let yValue;
            let label;
            <#list dataPoints as dataPoint>
            yValue = ${dataPoint.y};
            label = "${dataPoint.label}";
            dps.push({
                y: yValue,
                label: label
            })
            </#list>
            chart.render();
        })
    </script>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</#macro>