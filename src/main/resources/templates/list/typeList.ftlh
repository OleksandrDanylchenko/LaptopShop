<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Type">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger float-left" href="/type/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий тип ноутбуків</a>
        </div>
        <div class="col-1">
            <a class="btn btn-success float-right" href="/type/exportExcel" role="button"><i class="fas fa-file-excel"></i></a>
        </div>
        <div class="col-2">
            <div class="form-group float-right" id="typeSearch">
                <form method="get" action="/type" class="form-inline">
                    <input type="text" class="form-control" name="name" value="${RequestParameters.name!}"
                           placeholder="Назва"/>
                    <button type="submit" class="btn btn-danger ml-2"><i class="fa fa-search"></i></button>
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