<#import "../parts/htmlPad.ftlh" as pad>
<#import "../graph/pieGraphScript.ftlh" as pieGraphScript>

<@pad.page "Basket graphs">
    <div class="accordion" id="basketAccordion">
        <div class="card">
            <div class="card-header" id="employeeHeading">
                <h2 class="mb-0 text-center">
                    <button class="btn btn-danger btn-lg btn-block collapsed" type="button" data-toggle="collapse"
                            data-target="#employeeGraph" aria-expanded="true" aria-controls="employeeGraph">
                        Статистичний графік продажів щодо працівників
                    </button>
                </h2>
            </div>
            <div id="employeeGraph" class="collapse" aria-labelledby="employeeHeading" data-parent="#basketAccordion">
                <div id="employeeGraphContainer" class="w-100" style="height: 650px"></div>
            </div>
        </div>

        <div class="card">
            <div class="card-header" id="clientHeading">
                <h2 class="mb-0">
                    <button class="btn btn-dark btn-lg btn-block collapsed" type="button" data-toggle="collapse"
                            data-target="#clientGraph" aria-expanded="false" aria-controls="clientGraph">
                        Статистичний графік продажів щодо клієнтів
                    </button>
                </h2>
            </div>
            <div id="clientGraph" class="collapse" aria-labelledby="clientHeading" data-parent="#basketAccordion">
                <div class="card-body">
                    Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3
                    wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum
                    eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla
                    assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt
                    sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer
                    farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus
                    labore sustainable VHS.
                </div>
            </div>
        </div>
    </div>

    <@pieGraphScript.pieGraph
        collapseId="employeeGraph" graphContainer="employeeGraphContainer"
        titleText="Відсоток продаж щодо працівника" units="продажі" dataPoints=employeesDataPoints>
    </@pieGraphScript.pieGraph>
</@pad.page>