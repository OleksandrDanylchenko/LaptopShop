<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "SSD">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#ssdAddModal">
                <i class="far fa-plus-square"></i> Додати новий SSD диск
            </button>
            <@addModal.ssdAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/ssd/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/ssd/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
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
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="memory"
                                   value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
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
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#ssdEditModal${ssd.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.ssdEdit id="${ssd.id}" ssdModel="${ssd.model}" ssdMemory="${ssd.memory?c}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${ssd.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${ssd.id}"
                            deletionLink="/ssd/delete/${ssd.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного SSD диску.</div>
        </#if>
    </div>
</@htmlPad.page>