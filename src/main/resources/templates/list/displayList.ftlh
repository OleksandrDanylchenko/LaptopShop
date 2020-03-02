<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Display">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/display/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий дисплей</a>
        </div>
        <div class="col-1 mr-2 mr-sm-0">
            <a class="btn btn-success" href="/display/exportExcel" role="button"><i class="fas fa-file-excel"></i></a>
        </div>
        <div class="col-lg-4 col-5">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#displaySearch" aria-expanded="false" aria-controls="displaySearch">
                Знайти дисплей
            </button>
            <div class="collapse form-group" id="displaySearch">
                <form method="get" action="/display">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-4">
                            <input type="text" class="form-control mr-1 mt-2" name="type"
                                   value="${RequestParameters.type!}" placeholder="Тип"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-5">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="diagonal"
                                   value="${RequestParameters.diagonal!}" placeholder="Діагональ"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="resolution"
                                   value="${RequestParameters.resolution!}" placeholder="Розширення"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if displays?has_content>
            <div class="h1">Список співробітників:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Діагональ</th>
                    <th scope="col">Розширення</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list displays as display>
                    <tr>
                        <th class="text-danger" scope="row">${display.id}</th>
                        <td>${display.model}</td>
                        <td>${display.type}</td>
                        <td>${display.diagonal}</td>
                        <td>${display.resolution}</td>
                        <td><a class="btn btn-outline-dark" href="/display/edit/${display.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/display/delete/${display.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного дисплею.</div>
        </#if>
    </div>
</@pad.page>