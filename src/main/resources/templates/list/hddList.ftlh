<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "HDD">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/hdd/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий HDD диск</a>
        </div>

        <div class="col-lg-3 col-4">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#hddSearch" aria-expanded="false" aria-controls="hddSearch">
                Знайти HDD диск
            </button>
            <div class="collapse form-group" id="hddSearch">
                <form method="get" action="/hdd">
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${RequestParameters.model!}" placeholder="Модель"/>
                    <input type="number" min="1" class="form-control mr-1 mt-2" name="memory"
                           value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if hdds?has_content>
            <div class="h2">Список HDD дисків:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Пам'ять(GiB)</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list hdds as hdd>
                    <tr>
                        <th class="text-danger" scope="row">${hdd.id}</th>
                        <td>${hdd.model}</td>
                        <td>${hdd.memory}</td>
                        <td><a class="btn btn-outline-dark" href="/hdd/edit/${hdd.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/hdd/delete/${hdd.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного HDD диску.</div>
        </#if>
    </div>
</@pad.page>