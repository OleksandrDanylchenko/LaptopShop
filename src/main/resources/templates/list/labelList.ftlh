<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Label">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger float-left" href="/label/add" role="button">
                <i class="far fa-plus-square"></i> Додати нове найменування</a>
        </div>
        <div class="col-sm-1 col-3">
            <a class="btn btn-success" href="/label/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
        </div>
        <div class="col-sm-1 col-3">
            <a class="btn btn-success" href="/label/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#labelSearch" aria-expanded="false" aria-controls="labelSearch">
                Знайти найменування
            </button>
            <div class="collapse form-group" id="labelSearch">
                <form method="get" action="/label">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="brand"
                                   value="${RequestParameters.brand!}" placeholder="Бренд"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right"><i
                                class="fa fa-search"></i></button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if labels?has_content>
            <div class="h1">Список найменувань:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Бренд</th>
                    <th scope="col">Модель</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list labels as label>
                    <tr>
                        <th class="text-danger" scope="row">${label.id}</th>
                        <td>${label.brand}</td>
                        <td>${label.model}</td>
                        <td><a class="btn btn-outline-dark" href="/label/edit/${label.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/label/delete/${label.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного найменування.</div>
        </#if>
    </div>
</@pad.page>