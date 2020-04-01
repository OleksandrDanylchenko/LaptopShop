<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "HDD">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#hddAddModal">
                <i class="far fa-plus-square"></i> Додати новий HDD диск
            </button>
            <@addModal.hddAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/hdd/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/hdd/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#hddSearch" aria-expanded="false" aria-controls="hddSearch">
                Знайти HDD диск
            </button>
            <div class="collapse form-group" id="hddSearch">
                <form method="get" action="/hdd">
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
        <#if hdds?has_content>
            <div class="h1">Список HDD дисків:</div>
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
                <#list hdds as hdd>
                    <tr>
                        <th class="text-danger" scope="row">${hdd.id}</th>
                        <td>${hdd.model}</td>
                        <td>${hdd.memory}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#hddEditModal${hdd.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.hddEdit id="${hdd.id}" hddModel="${hdd.model}" hddMemory="${hdd.memory?c}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${hdd.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${hdd.id}"
                            deletionLink="/hdd/delete/${hdd.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного HDD диску.</div>
        </#if>
    </div>
</@htmlPad.page>