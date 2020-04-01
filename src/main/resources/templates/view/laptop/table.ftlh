<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "Laptop">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#laptopAddModal">
                <i class="far fa-plus-square"></i> Додати новий ноутбук
            </button>
            <@addModal.laptopAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/laptop/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/laptop/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#laptopSearch" aria-expanded="false" aria-controls="laptopSearch">
                Знайти ноутбук
            </button>
            <div class="collapse form-group" id="laptopSearch">
                <form method="get" action="/laptop">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="labelBrand"
                                   value="${RequestParameters.labelBrand!}" placeholder="Бренд"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="labelModel"
                                   value="${RequestParameters.labelModel!}" placeholder="Модель"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="typeName"
                                   value="${RequestParameters.typeName!}" placeholder="Тип"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="hardwareAssemblyName"
                                   value="${RequestParameters.hardwareAssemblyName!}" placeholder="Назва збірки"/>
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
        <#if laptops?has_content>
            <div class="h1">Список ноутбуків:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Бренд</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Збірка</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list laptops as laptop>
                    <tr>
                        <th class="text-danger" scope="row">${laptop.id}</th>
                        <td>${laptop.label.brand}</td>
                        <td>${laptop.label.model}</td>
                        <td><#if laptop.type??>${laptop.type.name}<#else>Відсутній</#if></td>
                        <td><#if laptop.hardware??>${laptop.hardware.assemblyName}<#else>Відсутній</#if></td>
                        <td><a class="btn btn-outline-dark" href="/laptop/edit/${laptop.id}"
                               role="button"><i class="fa fa-edit"></i></a>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${laptop.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${laptop.id}"
                            deletionLink="/laptop/delete/${laptop.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного ноутбуку.</div>
        </#if>
    </div>
</@htmlPad.page>