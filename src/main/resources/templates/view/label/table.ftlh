<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<@htmlPad.page "Label">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#labelAddModal">
                <i class="far fa-plus-square"></i> Додати нове найменування
            </button>
            <@addModal.labelAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#LabelExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Label" tableNameUk="найменувань"/>
            <a class="btn btn-success fixedBtnHeight" href="/label/exportExcel" role="button">
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
                    <button type="submit" class="btn btn-danger float-right"><i class="fa fa-search"></i></button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#labelEditModal${label.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.labelEdit id="${label.id?c}" labelBrand="${label.brand}" labelModel="${label.model}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${label.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${label.id?c}"
                            deletionLink="/label/delete/${label.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного найменування.</div>
        </#if>
    </div>
</@htmlPad.page>