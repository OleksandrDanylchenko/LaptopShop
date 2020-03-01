<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "SSD">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/ssd/add" role="button">
                <i class="far fa-plus-square"></i> Додати нове найменування</a>
        </div>

        <div class="col-lg-4 col-5">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#ssdSearch" aria-expanded="false" aria-controls="ssdSearch">
                Знайти SSD диск
            </button>
            <div class="collapse form-group" id="ssdSearch">
                <form method="get" action="/ssd">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-3">
                            <input type="number" min="1" class="form-control" name="memory"
                                   value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if ssds?has_content>
            <div class="h1">Список SSD дисків:</div>
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
                <#list ssds as ssd>
                    <tr>
                        <th class="text-danger" scope="row">${ssd.id}</th>
                        <td>${ssd.model}</td>
                        <td>${ssd.memory}</td>
                        <td><a class="btn btn-outline-dark" href="/ssd/edit/${ssd.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/ssd/delete/${ssd.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
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