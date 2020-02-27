<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/hardware/add" role="button">
                <i class="far fa-plus-square"></i> Додати нову збірку</a>
        </div>

        <div class="col-6">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#hardwareSearch" aria-expanded="false" aria-controls="hardwareSearch">
                Знайти збірку
            </button>
            <div class="collapse form-group" id="hardwareSearch">
                <form method="get" action="/hardware">
                    <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                           value="${RequestParameters.assemblyName!}" placeholder="Назва"/>
                    <div class="form-row">
                        <div class="col-6">
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
                                   value="${RequestParameters.ramMemory!}" placeholder="Об'єм оперативної пам'яті"/>
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
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if hardware?has_content>
            <div class="h2">Список збірок:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Назва збірки</th>
                    <th scope="col">Модель процесора</th>
                    <th scope="col">Модель відеокарти</th>
                    <th scope="col">Модель дисплею</th>
                    <th scope="col">Модель оперативної пам'яті</th>
                    <th scope="col">Модель SSD диску</th>
                    <th scope="col">Модель HDD диску</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list hardware as assembly>
                    <tr>
                        <th scope="row">${assembly.id}</th>
                        <td>${assembly.assemblyName}</td>
                        <td><#if assembly.cpu??>${assembly.cpu.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.gpu??>${assembly.gpu.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.display??>${assembly.display.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.ram??>${assembly.ram.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.ssd??>${assembly.ssd.model}<#else>Відсутня</#if></td>
                        <td><#if assembly.hdd??>${assembly.hdd.model}<#else>Відсутня</#if></td>
                        <td><a class="btn btn-outline-dark" href="/hardware/edit/${assembly.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/hardware/delete/${assembly.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодної збірки.</div>
        </#if>
    </div>
</@pad.page>