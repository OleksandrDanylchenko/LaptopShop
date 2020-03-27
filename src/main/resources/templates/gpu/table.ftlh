<#import "../parts/htmlPad.ftlh" as htmlPad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../gpu/addModal.ftlh" as addModal>
<#import "../gpu/editModal.ftlh" as editModal>
<#import "../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "GPU">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#gpuAddModal">
                <i class="far fa-plus-square"></i> Додати нову відеокарту
            </button>
            <@addModal.gpuAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/gpu/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/gpu/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#gpuSearch" aria-expanded="false" aria-controls="gpuSearch">
                Знайти відеокарту
            </button>
            <div class="collapse form-group" id="gpuSearch">
                <form method="get" action="/gpu">
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
        <#if gpus?has_content>
            <div class="h1">Список відеокарт:</div>
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
                <#list gpus as gpu>
                    <tr>
                        <th class="text-danger" scope="row">${gpu.id}</th>
                        <td>${gpu.model}</td>
                        <td>${gpu.memory}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#gpuEditModal${gpu.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.gpuEdit id="${gpu.id}" gpuModel="${gpu.model}" gpuMemory="${gpu.memory}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${gpu.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${gpu.id}"
                            deletionLink="/gpu/delete/${gpu.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодної відеокарти.</div>
        </#if>
    </div>
</@htmlPad.page>