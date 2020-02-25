<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "SSD">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-primary float-left" href="/ssd/add" role="button">
                <i class="far fa-plus-square"></i> Додати нове найменування</a>
        </div>

        <div class="col">
            <div class="row">
                <button class="btn btn-primary float-right w-50" type="button" data-toggle="collapse"
                        data-target="#cpuSearch" aria-expanded="false" aria-controls="cpuSearch">
                    Знайти процесор
                </button>
            </div>
            <div class="row">
                <div class="collapse form-group float-right w-50" id="cpuSearch">
                    <form method="get" action="/ssd">
                        <input type="text" class="form-control mr-1 mt-2" name="model"
                               value="${RequestParameters.model!}" placeholder="Модель"/>
                        <input type="number" min="1" class="form-control mr-1 mt-2" name="memory"
                               value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
                        <button type="submit" class="btn btn-primary ml-md-2 my-2 float-right"><i
                                    class="fa fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if ssds?has_content>
            <div class="h3">Список SSD дисків:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Пам'ять(GiB)</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list ssds as ssd>
                    <tr>
                        <th scope="row">${ssd.id}</th>
                        <td>${ssd.model}</td>
                        <td>${ssd.memory}</td>
                        <td><a class="btn btn-outline-primary" href="/ssd/edit/${ssd.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-primary" href="/ssd/delete/${ssd.id}"
                               role="button" onclick="return confirm('Are you sure?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного SSD диску.</div>
        </#if>
    </div>
</@pad.page>