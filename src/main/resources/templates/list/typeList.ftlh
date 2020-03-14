<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Type">

    <div class="d-flex flex-wrap">
        <div>
            <a class="btn btn-outline-danger" href="/type/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий тип ноутбуків</a>
        </div>
        <div class="d-flex justify-content-end flex-grow-1">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/type/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/type/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="flex-grow-1">
            <button class="btn btn-dark ml-0 ml-sm-2 mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#typeSearch" aria-expanded="false" aria-controls="typeSearch">
                Знайти тип
            </button>
            <div class="collapse form-group" id="typeSearch">
                <form method="get" action="/type">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="typeName"
                                   value="${RequestParameters.typeName!}" placeholder="Назва типу"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div class="row mt-2">
        <#if types?has_content>
            <div class="h1">Список типів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Назва</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list types as type>
                    <tr>
                        <th class="text-danger" scope="row">${type.id}</th>
                        <td>${type.name}</td>
                        <td><a class="btn btn-outline-dark" href="/type/edit/${type.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/type/delete/${type.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i>
                            </a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного типу.</div>
        </#if>
    </div>
</@pad.page>