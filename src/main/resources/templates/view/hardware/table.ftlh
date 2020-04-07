<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@htmlPad.page "Hardware">
    <div class="row">
        <div class="col">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                        data-target="#hardwareAddModal">
                    <i class="far fa-plus-square"></i> Додати нову збірку
                </button>
                <@addModal.hardwareAdd/>
            </@security.authorize>
        </div>
        <div class="d-flex justify-content-center">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-success mr-2" id="fixedBtnHeight" data-toggle="modal"
                        data-target="#HardwareExcelUploadModal">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
                </button>
                <@eU.excelUpload tableNameEn="Hardware" tableNameUk="збірок"/>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a class="btn btn-success" id="fixedBtnHeight" href="/hardware/exportExcel" role="button">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
                </a>
            </@security.authorize>
        </div>
        <div class="col-lg-6 col-sm-7 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#hardwareSearch" aria-expanded="false" aria-controls="hardwareSearch">
                Знайти збірку
            </button>
            <div class="collapse form-group" id="hardwareSearch">
                <form method="get" action="/hardware">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                                   value="${RequestParameters.assemblyName!}" placeholder="Назва"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="displayModel"
                                   value="${RequestParameters.displayModel!}" placeholder="Модель дисплею"/>
                        </div>
                        <div class="col">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2"
                                   name="displayDiagonal"
                                   value="${RequestParameters.displayDiagonal!}" placeholder="Діагональ дисплею"/>
                        </div>
                        <div class="col-3">
                            <input type="text" class="form-control mr-1 mt-2" name="displayType"
                                   value="${RequestParameters.displayType!}" placeholder="Тип дисплею"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="cpuModel"
                                   value="${RequestParameters.cpuModel!}" placeholder="Модель процесору"/>
                        </div>
                        <div class="col">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="cpuFrequency"
                                   value="${RequestParameters.cpuFrequency!}" placeholder="Частота процесору"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="gpuModel"
                                   value="${RequestParameters.gpuModel!}" placeholder="Модель відеокарти"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="gpuMemory"
                                   value="${RequestParameters.gpuMemory!}" placeholder="Об'єм відеопам'яті"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="ramModel"
                                   value="${RequestParameters.ramModel!}" placeholder="Модель оперативної пам'яті"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="ramMemory"
                                   value="${RequestParameters.ramMemory!}" placeholder="Об'єм опер. пам'яті"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="ssdModel"
                                   value="${RequestParameters.ssdModel!}" placeholder="Модель SSD диску"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="ssdMemory"
                                   value="${RequestParameters.ssdMemory!}" placeholder="Об'єм SSD диску"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-8">
                            <input type="text" class="form-control mr-1 mt-2" name="hddModel"
                                   value="${RequestParameters.hddModel!}" placeholder="Модель HDD диску"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control mr-1 mt-2" name="hddMemory"
                                   value="${RequestParameters.hddMemory!}" placeholder="Об'єм HDD диск"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
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
        <#if hardware?has_content>
            <div class="h1">Список збірок:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th class="text-danger" scope="col">Id</th>
                    </@security.authorize>

                    <th scope="col">Назва збірки</th>
                    <th scope="col">Модель процесора</th>
                    <th scope="col">Модель відеокарти</th>
                    <th scope="col">Модель дисплею</th>
                    <th scope="col">Модель оперативної пам'яті</th>
                    <th scope="col">Модель SSD диску</th>
                    <th scope="col">Модель HDD диску</th>

                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th></th>
                        <th></th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
                <#list hardware as assembly>
                    <tr>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <th class="text-danger" scope="row">${assembly.id}</th>
                        </@security.authorize>

                        <td>${assembly.assemblyName}</td>
                        <td><#if assembly.cpu??>${assembly.cpu.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.gpu??>${assembly.gpu.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.display??>${assembly.display.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.ram??>${assembly.ram.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.ssd??>${assembly.ssd.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.hdd??>${assembly.hdd.model}<#else>Відсутня</#if></td>

                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <td>
                                <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#hardwareEditModal${assembly.id?c}">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <@editModal.hardwareEdit id = "${assembly.id?c}"
                                currentAssemblyName = "${assembly.assemblyName}"
                                currentCpuModel = "${(assembly.cpu??)?then(assembly.cpu.model, '')}"
                                currentGpuModel = "${(assembly.gpu??)?then(assembly.gpu.model, '')}"
                                currentDisplayModel = "${(assembly.display??)?then(assembly.display.model, '')}"
                                currentRamModel = "${(assembly.ram??)?then(assembly.ram.model, '')}"
                                currentSsdModel = "${(assembly.ssd??)?then(assembly.ssd.model, '')}"
                                currentHddModel = "${(assembly.hdd??)?then(assembly.hdd.model, '')}"
                                />
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#deleteModal${assembly.id?c}">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <@deleteModal.delete id="${assembly.id?c}"
                                deletionLink="/hardware/delete/${assembly.id?c}"/>
                            </td>
                        </@security.authorize>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодної збірки.</div>
        </#if>
    </div>
</@htmlPad.page>