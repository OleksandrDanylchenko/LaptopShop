<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Label">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-primary float-left" href="/label/add" role="button">
                <i class="far fa-plus-square"></i> Додати нове найменування</a>
        </div>

        <div class="col-lg-3 col-4">
            <button class="btn btn-primary w-100" type="button" data-toggle="collapse"
                    data-target="#labelSearch" aria-expanded="false" aria-controls="labelSearch">
                Знайти найменування
            </button>
            <div class="collapse form-group" id="labelSearch">
                <form method="get" action="/label">
                    <label class="sr-only" for="labelSearch">Шукане найменування</label>
                    <input type="text" class="form-control mr-1 mt-2" name="brand"
                           value="${RequestParameters.brand!}" placeholder="Бренд"/>
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${RequestParameters.model!}" placeholder="Модель"/>
                    <button type="submit" class="btn btn-primary ml-md-2 my-2 float-right"><i
                                class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div class="row mt-5">
        <#if labels?has_content>
            <div class="h3">Список найменувань:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Бренд</th>
                    <th scope="col">Модель</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list labels as label>
                    <tr>
                        <th scope="row">${label.id}</th>
                        <td>${label.brand}</td>
                        <td>${label.model}</td>
                        <td><a class="btn btn-outline-primary" href="/label/edit/${label.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-primary" href="/label/delete/${label.id}"
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