<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>

<@htmlPad.page "Type">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#typeAddModal">
                <i class="far fa-plus-square"></i> Додати новий тип ноутбуків
            </button>
            <@addModal.typeAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/type/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/type/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
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

    <div class="mt-4">
        <div class="row">
            <div class="col">
                <@eO.errorOutput errorMessage!/>
            </div>
        </div>
        <#if types?has_content>
            <div class="row">
                <div class="col">
                    <div class="h1">Список типів:</div>
                </div>
            </div>
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
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#typeEditModal${type.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.typeEdit id="${type.id}" typeName="${type.name}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${type.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${type.id}" deletionLink="/type/delete/${type.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного типу.</div>
        </#if>
    </div>
</@htmlPad.page>