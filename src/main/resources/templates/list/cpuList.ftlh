<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "CPU">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger " href="/cpu/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий процесор</a>
        </div>

        <div class="col-lg-3 col-4">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#cpuSearch" aria-expanded="false" aria-controls="cpuSearch">
                Знайти процесор
            </button>
            <div class="collapse form-group" id="cpuSearch">
                <form method="get" action="/cpu">
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${RequestParameters.model!}" placeholder="Модель"/>
                    <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="frequency"
                           value="${RequestParameters.frequency!}" placeholder="Частота"/>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if cpus?has_content>
            <div class="h2">Список процесорів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Частота(GHz)</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list cpus as cpu>
                    <tr>
                        <th scope="row">${cpu.id}</th>
                        <td>${cpu.model}</td>
                        <td>${cpu.frequency}</td>
                        <td><a class="btn btn-outline-dark" href="/cpu/edit/${cpu.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/cpu/delete/${cpu.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного процесору.</div>
        </#if>
    </div>
</@pad.page>