<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@htmlPad.page "CPU">
    <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
        <div class="container">
    </@security.authorize>
    <div class="row">
        <div class="col">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                        data-target="#cpuAddModal">
                    <i class="far fa-plus-square"></i> Додати новий процесор
                </button>
                <@addModal.cpuAdd/>
            </@security.authorize>
        </div>
        <div class="d-flex justify-content-center">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-success mr-2" id="fixedBtnHeight" data-toggle="modal"
                        data-target="#CpuExcelUploadModal">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
                </button>
                <@eU.excelUpload tableNameEn="Cpu" tableNameUk="процесорів"/>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a class="btn btn-success" id="fixedBtnHeight" href="/cpu/exportExcel" role="button">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
                </a>
            </@security.authorize>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#cpuSearch" aria-expanded="false" aria-controls="cpuSearch">
                Знайти процесор
            </button>
            <div class="collapse form-group" id="cpuSearch">
                <form method="get" action="/cpu">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-4">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="frequency"
                                   value="${RequestParameters.frequency!}" placeholder="Частота"/>
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
        <#if cpus?has_content>
            <div class="h1">Список процесорів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th class="text-danger" scope="col">Id</th>
                    </@security.authorize>

                    <th scope="col">Модель</th>
                    <th scope="col">Частота(GHz)</th>

                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th></th>
                        <th></th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
                <#list cpus as cpu>
                    <tr>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <th class="text-danger" scope="row">${cpu.id}</th>
                        </@security.authorize>

                        <td>${cpu.model}</td>
                        <td>${cpu.frequency}</td>

                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <td>
                                <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#cpuEditModal${cpu.id?c}">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <@editModal.cpuEdit id="${cpu.id?c}" cpuModel="${cpu.model}" cpuFrequency="${cpu.frequency}"/>
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#deleteModal${cpu.id?c}">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <@deleteModal.delete id="${cpu.id?c}"
                                deletionLink="/cpu/delete/${cpu.id?c}"/>
                            </td>
                        </@security.authorize>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного процесору.</div>
        </#if>
    </div>
    <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
        </div>
    </@security.authorize>
</@htmlPad.page>