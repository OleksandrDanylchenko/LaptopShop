<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<@htmlPad.page "Shops">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#shopAddModal">
                <i class="far fa-plus-square"></i> Додати новий магазин
            </button>
            <@addModal.shopAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2" id="fixedBtnHeight" data-toggle="modal"
                    data-target="#ShopExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Shop" tableNameUk="магазинів"/>
            <a class="btn btn-success" id="fixedBtnHeight" href="/shop/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#shopSearch" aria-expanded="false" aria-controls="shopSearch">
                Знайти магазин
            </button>
            <div class="collapse form-group" id="shopSearch">
                <form method="get" action="/shop">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="address"
                                   value="${RequestParameters.address!}" placeholder="Адреса магазину"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
                        <i class="fa fa-search"></i>
                    </button>
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
        <#if shops?has_content>
            <div class="h1">Список магазинів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Адреса</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list shops as shop>
                    <tr>
                        <th class="text-danger" scope="row">${shop.id}</th>
                        <td>${shop.address}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#shopEditModal${shop.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.shopEdit id="${shop.id}" shopAddress="${shop.address}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${shop.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${shop.id}" deletionLink="/shop/delete/${shop.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного магазину.</div>
        </#if>
    </div>
</@htmlPad.page>